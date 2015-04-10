package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.TemplateMap;

import static org.junit.Assert.assertEquals;

public class AbstractSyntaxTreeIfTest {

    @Test
    public void normalIf() {
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.If(
                        new AbstractSyntaxTree.Id("condition"), new AbstractSyntaxTree.Id("if_statements"),
                        new AbstractSyntaxTree.Id("else_statements")
                );

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.add("if_statements", "condition was true");
        arguments.add("else_statements", "condition was false");
        arguments.add("condition", true);

        Object result = abs.run(arguments);
        assertEquals("condition was true", result);

        arguments.add("condition", false);
        result = abs.run(arguments);
        assertEquals("condition was false", result);
    }

    @Test
    public void ifConditionWithNestedProperties() {
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.If(
                        new AbstractSyntaxTree.Id("condition",
                                new AbstractSyntaxTree.Id("nested_condition")), new AbstractSyntaxTree.Id("if_statements"),
                        new AbstractSyntaxTree.Id("else_statements")
                );

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.add("if_statements", "nested condition was true");
        arguments.add("else_statements", "nested condition was false");

        arguments.addMappedObject("condition", null, (ignore, map) -> map.add("nested_condition", true));
        Object result = abs.run(arguments);
        assertEquals("nested condition was true", result);

        arguments.addMappedObject("condition", null, (ignore, map) -> map.add("nested_condition", false));
        result = abs.run(arguments);
        assertEquals("nested condition was false", result);
    }

    @Test
    public void ifWithoutElse() {
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.If(
                        new AbstractSyntaxTree.Id("condition"), new AbstractSyntaxTree.Id("if_statements")
                );

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.add("if_statements", "condition was true");
        arguments.add("condition", true);

        Object result = abs.run(arguments);
        assertEquals("condition was true", result);

        arguments.add("condition", false);
        result = abs.run(arguments);
        assertEquals("", result);
    }
}
