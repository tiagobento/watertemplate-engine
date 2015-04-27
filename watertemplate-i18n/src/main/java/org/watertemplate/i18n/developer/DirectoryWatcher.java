package org.watertemplate.i18n.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.watertemplate.i18n.GenerationMojo;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static com.sun.nio.file.SensitivityWatchEventModifier.HIGH;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.*;

public class DirectoryWatcher {

    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryWatcher.class);

    public DirectoryWatcher(final String... dirs) {
        try {
            this.watcher = FileSystems.getDefault().newWatchService();
            this.keys = new HashMap<>();

            registerAll(dirs);
            LOGGER.info("Watching {}", dirs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void watchRunning(final Runnable r) {
        final Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        WatchKey key = watcher.take();
                        Path dir = keys.get(key);

                        for (WatchEvent<?> event : key.pollEvents()) {
                            WatchEvent<Path> ev = cast(event);
                            Path child = dir.resolve(ev.context());

                            LOGGER.info(event.kind().name());
                            r.run();

                            if (event.kind() == ENTRY_CREATE) {
                                if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                                    registerAll(child);
                                }
                            }
                        }

                        boolean valid = key.reset();
                        if (!valid) {
                            keys.remove(key);
                            if (keys.isEmpty()) {
                                break;
                            }
                        }

                    } catch (Exception e) {
                        LOGGER.error("Error rebuilding templates", e);
                    }
                }
            }
        };

        thread.start();
    }

    private void registerAll(final String... dirs) throws IOException {
        for (String dir : dirs) {
            registerAll(Paths.get(dir));
        }
    }

    private void registerAll(Path dirPath) throws IOException {
        final WatchEvent.Kind[] events = {ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY};
        Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                WatchKey key = dir.register(watcher, events, HIGH);
                keys.put(key, dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
}
