package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.TemplateMap;

import static org.junit.Assert.*;

public class AbstractSyntaxTreeTextTest {

    @Test
    public void normalText() {
        AbstractSyntaxTree abstractSyntaxTree = new AbstractSyntaxTree.Text("text");
        Object result = abstractSyntaxTree.run(new TemplateMap.Arguments());

        assertEquals("text", result);
    }
}