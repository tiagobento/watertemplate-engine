package org.watertemplate.interpreter.parser.abs;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.exception.TemplateException;

import java.util.HashMap;
import java.util.Map;

public class IdCommandTest {

    @Test
    public void idWithOnlyOnePropertyKey() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IdCommand("prop_key"));

        Map<String, Object> args = new HashMap<>();
        args.put("prop_key", "success");

        String result = abs.run(args);
        Assert.assertEquals("success", result);
    }

    @Test(expected = TemplateException.class)
    public void wrongIdNestedProperties() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IdCommand("prop_key",
                        new IdCommand("nested_property")));

        Map<String, Object> args = new HashMap<>();
        args.put("prop_key", "success");

        abs.run(args);
    }

    @Test
    public void idWithNestedProperty() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IdCommand("prop_key",
                        new IdCommand("nested_prop_key",
                                new IdCommand("nested_nested_prop_key"))));

        Map<String, Object> args2 = new HashMap<>();
        args2.put("nested_nested_prop_key", "success");

        Map<String, Object> args1 = new HashMap<>();
        args1.put("nested_prop_key", args2);

        Map<String, Object> args0 = new HashMap<>();
        args0.put("prop_key", args1);

        String result = abs.run(args0);
        Assert.assertEquals("success", result);
    }

    @Test
    public void propertyNotPresentInArguments() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IdCommand("prop_key"));

        String result = abs.run(new HashMap<>());
        Assert.assertEquals("prop_key", result);
    }
}