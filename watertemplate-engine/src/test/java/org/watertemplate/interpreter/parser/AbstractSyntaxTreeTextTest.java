package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.TemplateMap;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class AbstractSyntaxTreeTextTest {

    private static final Locale locale = Locale.US;

    @Test
    public void normalText() {
        AbstractSyntaxTree abstractSyntaxTree = new AbstractSyntaxTree.Text("text");
        Object result = abstractSyntaxTree.string(new TemplateMap.Arguments(), locale);

        assertEquals("text", result);
    }
}