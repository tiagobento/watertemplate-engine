package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.TemplateMap;
import org.watertemplate.exception.TemplateException;
import org.watertemplate.interpreter.parser.exception.IdCouldNotBeResolvedException;

import static org.junit.Assert.assertEquals;

public class AbstractSyntaxTreeIdTest {

    @Test
    public void idWithOnlyOnePropertyKey() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs = new AbstractSyntaxTree.Id("prop_key");

        arguments.add("prop_key", "success");

        Object result = abs.run(arguments);
        assertEquals("success", result);
    }

    @Test
    public void idWithNestedProperties() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.Id("prop_key",
                        new AbstractSyntaxTree.Id("nested_prop_key",
                                new AbstractSyntaxTree.Id("nested_nested_prop_key")));

        arguments.addMappedObject("prop_key", null, (ignore, map) -> {
            map.addMappedObject("nested_prop_key", null, (ignore2, nestedMap) -> {
                nestedMap.add("nested_nested_prop_key", "success");
            });
        });

        Object result = abs.run(arguments);
        assertEquals("success", result);
    }

    @Test(expected = IdCouldNotBeResolvedException.class)
    public void tooManyNestedProperties() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.Id("prop_key",
                        new AbstractSyntaxTree.Id("nested1",
                                new AbstractSyntaxTree.Id("nested2",
                                        new AbstractSyntaxTree.Id("nested3")
                                )
                        )
                );

        arguments.add("prop_key", "success");
        abs.run(arguments);
    }

    @Test(expected = IdCouldNotBeResolvedException.class)
    public void propertyNotPresentInArguments() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree.Id("prop_key");

        Object result = abs.run(new TemplateMap.Arguments());
        assertEquals("prop_key", result);
    }
}
