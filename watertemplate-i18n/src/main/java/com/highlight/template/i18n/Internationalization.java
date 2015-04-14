package com.highlight.template.i18n;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.TemplateFunction;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Internationalization {

    private static final Logger LOGGER = LoggerFactory.getLogger(Internationalization.class);

    private final File baseDir;
    private final File destinationDir;
    private final Set<File> alreadyCompiledDirs;
    private final Map<Locale, File> locales;

    public Internationalization(final String baseDir, final String destinationDir, final String bundlesDir) {
        this.baseDir = new File(baseDir);
        this.destinationDir = new File(destinationDir);
        this.alreadyCompiledDirs = new HashSet<>();
        this.locales = new HashMap<>();

        for (File bundleFile : new File(bundlesDir).listFiles()) {
            String[] language_country = FilenameUtils.removeExtension(bundleFile.getName()).split("_");
            if (language_country.length == 1) {
                locales.put(new Locale(language_country[0]), bundleFile);
            } else {
                locales.put(new Locale(language_country[0], language_country[1]), bundleFile);
            }
        }
    }

    public void parse() throws IOException {
        try {
            long start = System.currentTimeMillis();
            createAndCompileFiles();
            LOGGER.info("All files parsed in {}ms", System.currentTimeMillis() - start);
        } catch (Exception e) {
            LOGGER.error("Deleting i18n directories.");
            deleteFilesOrDirectories(alreadyCompiledDirs);
            throw e;
        }
    }

    private void createAndCompileFiles() throws IOException {
        for (final Locale locale : locales.keySet()) {
            try {
                File localeDir = new File(destinationDir, locale.toString());
                LOGGER.info("Parsing {} at {}", locale, localeDir);

                FileUtils.deleteDirectory(localeDir);
                FileUtils.copyDirectory(baseDir, localeDir);

                alreadyCompiledDirs.add(localeDir);
                compileFilesAndDirectoriesFor(locale, localeDir);
            } catch (Exception e) {
                LOGGER.error("Error processing {}", locale);
                throw e;
            }
        }
    }

    private void compileFilesAndDirectoriesFor(final Locale locale, final File dir) throws IOException {
        File[] files = dir.listFiles();

        if (files == null)  {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                compileFilesAndDirectoriesFor(locale, file);
            } else {
                compileFile(locale, file);
            }
        }
    }

    private void compileFile(Locale locale, File file) throws IOException {
        LOGGER.debug("Parsing {} at {}", locale, file);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Before: {} ", FileUtils.readFileToString(file).trim());
        }

        final Properties p = new Properties();
        p.load(new FileReader(locales.get(locale)));

        Map<String, Object> func = new HashMap<>();
        func.put("i18n", (TemplateFunction) s -> p.getProperty(s, s));

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(file.getAbsolutePath());
        mustache.execute(new FileWriter(file), func).flush();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("After: {} ", FileUtils.readFileToString(file));
        }
    }

    ///

    private static void deleteFilesOrDirectories(Collection<File> files) throws IOException {
        for (File file : files.toArray(new File[files.size()])) {
            if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            } else {
                file.delete();
            }
        }
    }
}
