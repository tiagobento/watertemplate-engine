package org.watertemplate.interpreter.reader;

import org.watertemplate.exception.TemplateException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.function.Consumer;

public class TemplateReader {
    private static final int BUFFER_SIZE = 8192;

    private final File templateFile;

    public TemplateReader(final File templateFile) {
        this.templateFile = templateFile;
    }

    public void readExecuting(final Consumer<Character> consumer) {
        try {
            final char[] buffer = new char[BUFFER_SIZE];
            final BufferedReader bf = new BufferedReader(new FileReader(templateFile));
            for (int nReadChars; (nReadChars = bf.read(buffer, 0, BUFFER_SIZE)) != -1; ) {
                for (int i = 0; i < nReadChars; i++) {
                    consumer.accept(buffer[i]);
                }
            }
            consumer.accept('\0');
            bf.close();
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }
}
