package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.TemplateMap;
import org.watertemplate.exception.InvalidTemplateObjectEvaluationException;
import org.watertemplate.interpreter.parser.exception.IdCouldNotBeResolvedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class AbstractSyntaxTreeForTest {

    private static final Locale locale = Locale.US;

    @Test
    public void normalFor() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.For("x", new AbstractSyntaxTree.Id("collection"),
                        new AbstractSyntaxTree.Id("x"),
                        new AbstractSyntaxTree.Text("collection has no elements"));

        arguments.addCollection("collection", Arrays.asList("a", "v", "3", "%", "5", "4", "7"));
        Object result = abs.evaluate(arguments, locale);
        assertEquals("av3%547", result);

        arguments.addCollection("collection", new ArrayList<>());
        result = abs.evaluate(arguments, locale);
        assertEquals("collection has no elements", result);

        arguments.addCollection("collection", null);
        result = abs.evaluate(arguments, locale);
        assertEquals("collection has no elements", result);
    }

    @Test
    public void forWithoutElse() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.For("x", new AbstractSyntaxTree.Id("collection"),
                        new AbstractSyntaxTree.Id("x")
                );

        arguments.addCollection("collection", Arrays.asList("a", "v", "3", "%", "5", "4", "7"));
        Object result = abs.evaluate(arguments, locale);
        assertEquals("av3%547", result);

        arguments.addCollection("collection", new ArrayList<>());
        result = abs.evaluate(arguments, locale);
        assertEquals("", result);

        arguments.addCollection("collection", null);
        result = abs.evaluate(arguments, locale);
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
        Object result = abs.evaluate(arguments, locale);
        assertEquals("abcd", result);

        arguments.addCollection("collection", new ArrayList<>());
        result = abs.evaluate(arguments, locale);
        assertEquals("", result);

        arguments.addCollection("collection", null);
        result = abs.evaluate(arguments, locale);
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

        Object result = abs.evaluate(arguments, locale);
        assertEquals("ABCD", result);
    }

    @Test(expected = InvalidTemplateObjectEvaluationException.class)
    public void forTryingToEvaluateMappedObjectWhichAreNotStrings() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs =
                new AbstractSyntaxTree.For("x", new AbstractSyntaxTree.Id("collection"),
                        new AbstractSyntaxTree.Id("x"));

        arguments.addCollection("collection", Arrays.asList(1, 2, 3, 4), (number, map) -> {
            map.add("to_string", number.toString());
        });

        abs.evaluate(arguments, locale);
    }

    @Test(expected = IdCouldNotBeResolvedException.class)
    public void forTryingToEvaluateIterationIdentifierOutOfScope() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs = new AbstractSyntaxTree.Statements(Arrays.asList(
                new AbstractSyntaxTree.For("x", new AbstractSyntaxTree.Id("collection"),
                        new AbstractSyntaxTree.Id("x")
                ),
                new AbstractSyntaxTree.Id("x")
        ));

        arguments.addCollection("collection", Arrays.asList("a", "b", "c", "d"));
        abs.evaluate(arguments, locale);
    }
}
