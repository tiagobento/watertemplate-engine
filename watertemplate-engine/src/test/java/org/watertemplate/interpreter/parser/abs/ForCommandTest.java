package org.watertemplate.interpreter.parser.abs;

import org.junit.Test;
import org.watertemplate.TemplateMap;

import java.util.*;

import static org.junit.Assert.*;

public class ForCommandTest {

    @Test
    public void normalFor() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new ForCommand("x", new IdCommand("collection"),
                        new IdCommand("x"),
                        new TextCommand("collection has no elements"))
        );

        arguments.addCollection("collection", Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        String result = abs.run(arguments);
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
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new ForCommand("x", new IdCommand("collection"),
                        new IdCommand("x")
                ));

        arguments.addCollection("collection", Arrays.asList("a", 'v', 3, "%", 5, "4", 7));
        String result = abs.run(arguments);
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
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new ForCommand("x", new IdCommand("collection"),
                        new IdCommand("x")
                ));

        arguments.addCollection("collection", Arrays.asList("a", "b", "c", "d"));
        String result = abs.run(arguments);
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
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new ForCommand("x", new IdCommand("collection"),
                    new IdCommand("x", new IdCommand("upper"))
                ));

        arguments.addCollection("collection", Arrays.asList("a", "b", "c", "d"), (letter, map) -> {
            map.add("upper", letter.toUpperCase());
        });

        String result = abs.run(arguments);
        assertEquals("ABCD", result);
    }
}