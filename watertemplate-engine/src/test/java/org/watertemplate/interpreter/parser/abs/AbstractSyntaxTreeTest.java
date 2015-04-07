package org.watertemplate.interpreter.parser.abs;

import org.junit.Test;
import org.watertemplate.TemplateMap;
import org.watertemplate.exception.TemplateException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AbstractSyntaxTreeTest {
    public static class ForTest {

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

    public static class IdWithNestedPropertiesTest {

        @Test
        public void idWithOnlyOnePropertyKey() {
            AbstractSyntaxTree abs =
                    new AbstractSyntaxTree.Id("prop_key");

            TemplateMap.Arguments arguments = new TemplateMap.Arguments();
            arguments.add("prop_key", "success");

            Object result = abs.run(arguments);
            assertEquals("success", result);
        }

        @Test(expected = TemplateException.class)
        public void wrongIdNestedProperties() {
            AbstractSyntaxTree abs =
                    new AbstractSyntaxTree.Id("prop_key",
                            new AbstractSyntaxTree.Id("nested_property"));

            TemplateMap.Arguments arguments = new TemplateMap.Arguments();
            arguments.add("prop_key", "success");

            abs.run(arguments);
        }

        @Test
        public void idWithNestedProperty() {
            AbstractSyntaxTree abs =
                    new AbstractSyntaxTree.Id("prop_key",
                            new AbstractSyntaxTree.Id("nested_prop_key",
                                    new AbstractSyntaxTree.Id("nested_nested_prop_key")));

            TemplateMap.Arguments arguments = new TemplateMap.Arguments();
            arguments.addMappedObject("prop_key", null, (ignore, map) -> {
                map.addMappedObject("nested_prop_key", null, (ignore2, nestedMap) -> {
                    nestedMap.add("nested_nested_prop_key", "success");
                });
            });

            Object result = abs.run(arguments);
            assertEquals("success", result);
        }

        @Test
        public void propertyNotPresentInArguments() {
            AbstractSyntaxTree abs =
                    new AbstractSyntaxTree.Id("prop_key");

            Object result = abs.run(new TemplateMap.Arguments());
            assertEquals("prop_key", result);
        }
    }

    public static class IfTest {

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

    public static class StatementsTest {

        @Test
        public void sequentialStatements() {
            AbstractSyntaxTree abs =
                    new AbstractSyntaxTree.Statements(
                            new AbstractSyntaxTree.Id("x"),
                            new AbstractSyntaxTree.Text("\n"),
                            new AbstractSyntaxTree.Id("y"),
                            new AbstractSyntaxTree.Text("\nrandom text\n"),
                            new AbstractSyntaxTree.If(new AbstractSyntaxTree.Id("condition"),
                                    new AbstractSyntaxTree.Text("condition was true")
                            ),
                            new AbstractSyntaxTree.For("i", new AbstractSyntaxTree.Id("collection"),
                                    new AbstractSyntaxTree.Statements(
                                            new AbstractSyntaxTree.Text("\n"),
                                            new AbstractSyntaxTree.Id("i", new AbstractSyntaxTree.Id("square"))
                                    )
                            )
                    );

            TemplateMap.Arguments arguments = new TemplateMap.Arguments();
            arguments.add("x", "line 1");
            arguments.add("y", "line 2");
            arguments.add("condition", true);
            arguments.addCollection("collection", Arrays.asList(1, 2, 3, 4), (i, map) -> {
                map.add("square", Math.round(Math.pow(i, 2)));
            });

            Object result = abs.run(arguments);
            assertEquals("line 1\nline 2\nrandom text\ncondition was true\n1\n4\n9\n16", result);
        }

    }
}
