package org.watertemplate.interpreter.parser.abs;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class IfCommandTest {

    @Test
    public void normalIf() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IfCommand(
                        new IdCommand("condition"), new IdCommand("if_statements"),
                        new IdCommand("else_statements")
                ));

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("if_statements", "condition was true");
        arguments.put("else_statements", "condition was false");
        arguments.put("condition", true);

        String result = abs.run(arguments);
        assertEquals("condition was true", result);

        arguments.put("condition", false);
        result = abs.run(arguments);
        assertEquals("condition was false", result);
    }

    @Test
    public void ifConditionWithNestedProperties() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IfCommand(
                        new IdCommand("condition",
                                new IdCommand("nested_condition")), new IdCommand("if_statements"),
                        new IdCommand("else_statements")
                ));

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("if_statements", "nested condition was true");
        arguments.put("else_statements", "nested condition was false");

        Map<String, Object> conditionObject = new HashMap<>();
        conditionObject.put("nested_condition", true);

        arguments.put("condition", conditionObject);

        String result = abs.run(arguments);
        assertEquals("nested condition was true", result);

        conditionObject.put("nested_condition", false);
        result = abs.run(arguments);
        assertEquals("nested condition was false", result);
    }

    @Test
    public void ifWithoutElse() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IfCommand(
                        new IdCommand("condition"), new IdCommand("if_statements")
                ));

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("if_statements", "condition was true");
        arguments.put("condition", true);

        String result = abs.run(arguments);
        assertEquals("condition was true", result);

        arguments.put("condition", false);
        result = abs.run(arguments);
        assertEquals("", result);
    }
}