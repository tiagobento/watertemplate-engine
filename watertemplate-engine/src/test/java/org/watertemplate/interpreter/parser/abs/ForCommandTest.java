package org.watertemplate.interpreter.parser.abs;

import org.junit.Test;
import org.watertemplate.TemplateMap;

import java.util.*;

import static org.junit.Assert.*;

public class ForCommandTest {

    @Test
    public void normalFor() {
        Map<String, Object> arguments = new HashMap<>();
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new ForCommand("x", new IdCommand("list"),
                        new IdCommand("x"),
                        new TextCommand("list has no elements"))
        );

        arguments.put("list", Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        String result = abs.run(arguments);
        assertEquals("1234567", result);

        arguments.put("list", new ArrayList<>());
        result = abs.run(arguments);
        assertEquals("list has no elements", result);

        arguments.put("list", null);
        result = abs.run(arguments);
        assertEquals("list has no elements", result);
    }

    @Test
    public void forWithoutElse() {
        Map<String, Object> arguments = new HashMap<>();
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new ForCommand("x", new IdCommand("list"),
                        new IdCommand("x")
        ));

        arguments.put("list", Arrays.asList("a", 'v', 3, "%", 5, "4", 7));
        String result = abs.run(arguments);
        assertEquals("av3%547", result);

        arguments.put("list", new ArrayList<>());
        result = abs.run(arguments);
        assertEquals("", result);

        arguments.put("list", null);
        result = abs.run(arguments);
        assertEquals("", result);
    }

    @Test
    public void forUsingItsVariable() {
        Map<String, Object> arguments = new HashMap<>();
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new ForCommand("x", new IdCommand("list"),
                        new IdCommand("x")
                ));

        arguments.put("list", Arrays.asList("a", "b", "c", "d"));
        String result = abs.run(arguments);
        assertEquals("abcd", result);

        arguments.put("list", new ArrayList<>());
        result = abs.run(arguments);
        assertEquals("", result);

        arguments.put("list", null);
        result = abs.run(arguments);
        assertEquals("", result);
    }
}