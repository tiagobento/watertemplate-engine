package org.watertemplate.interpreter.parser.abs;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.TemplateMap;
import org.watertemplate.exception.TemplateException;

public class IdCommandTest {

    @Test
    public void idWithOnlyOnePropertyKey() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IdCommand("prop_key"));

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.add("prop_key", "success");

        String result = abs.run(arguments);
        Assert.assertEquals("success", result);
    }

    @Test(expected = TemplateException.class)
    public void wrongIdNestedProperties() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IdCommand("prop_key",
                        new IdCommand("nested_property")));

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.add("prop_key", "success");

        abs.run(arguments);
    }

    @Test
    public void idWithNestedProperty() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IdCommand("prop_key",
                        new IdCommand("nested_prop_key",
                                new IdCommand("nested_nested_prop_key"))));

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.addMappedObject("prop_key", null, (ignore, map) -> {
            map.addMappedObject("nested_prop_key", null, (ignore2, nestedMap) -> {
                nestedMap.add("nested_nested_prop_key", "success");
            });
        });

        String result = abs.run(arguments);
        Assert.assertEquals("success", result);
    }

    @Test
    public void propertyNotPresentInArguments() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new IdCommand("prop_key"));

        String result = abs.run(new TemplateMap.Arguments());
        Assert.assertEquals("prop_key", result);
    }
}