package org.watertemplate.interpreter.parser.abs;

import org.junit.Test;
import org.watertemplate.TemplateMap;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ForTest {

    @Test
    public void normalFor() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.For("x", new AbstractSyntaxTree.Id("collection"),
                        new AbstractSyntaxTree.Id("x"),
                        new AbstractSyntaxTree.Text("collection has no elements"));

        arguments.addCollection("collection", Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        Object result = abs.run(arguments);
        assertEquals("1234567", result);

        arguments.addCollection("collection", new ArrayList<>());
        result = abs.run(arguments);
        assertEquals("collection has no elements", result);

        arguments.addCollection("collection", null);
        result = abs.run(arguments);
        assertEquals("collection has no elements", result);
    }

    @Test
    public void forWithoutElse() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.For("x", new AbstractSyntaxTree.Id("collection"),
                        new AbstractSyntaxTree.Id("x")
                );

        arguments.addCollection("collection", Arrays.asList("a", 'v', 3, "%", 5, "4", 7));
        Object result = abs.run(arguments);
        assertEquals("av3%547", result);

        arguments.addCollection("collection", new ArrayList<>());
        result = abs.run(arguments);
        assertEquals("", result);

        arguments.addCollection("collection", null);
        result = abs.run(arguments);
        assertEquals("", result);
    }

    @Test
    public void forUsingItsVariable() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.For("x", new AbstractSyntaxTree.Id("collection"),
                        new AbstractSyntaxTree.Id("x")
                );

        arguments.addCollection("collection", Arrays.asList("a", "b", "c", "d"));
        Object result = abs.run(arguments);
        assertEquals("abcd", result);

        arguments.addCollection("collection", new ArrayList<>());
        result = abs.run(arguments);
        assertEquals("", result);

        arguments.addCollection("collection", null);
        result = abs.run(arguments);
        assertEquals("", result);
    }

    @Test
    public void forUsingItsVariablesMappedProperties() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.For("x", new AbstractSyntaxTree.Id("collection"),
                        new AbstractSyntaxTree.Id("x", new AbstractSyntaxTree.Id("upper")));

        arguments.addCollection("collection", Arrays.asList("a", "b", "c", "d"), (letter, map) -> {
            map.add("upper", letter.toUpperCase());
        });

        Object result = abs.run(arguments);
        assertEquals("ABCD", result);
    }
}
