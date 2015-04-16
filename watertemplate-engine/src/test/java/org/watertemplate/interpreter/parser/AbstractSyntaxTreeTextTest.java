package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.Configuration;
import org.watertemplate.TemplateMap;

import java.util.Locale;

import static org.junit.Assert.*;

public class AbstractSyntaxTreeTextTest {

    private static final Locale locale = Locale.US;

    @Test
    public void normalText() {
        AbstractSyntaxTree abstractSyntaxTree = new AbstractSyntaxTree.Text("text");
        Object result = abstractSyntaxTree.run(new TemplateMap.Arguments(), locale, Configuration.DEFAULT);

        assertEquals("text", result);
    }
}