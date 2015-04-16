package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.Configuration;
import org.watertemplate.TemplateMap;

import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class AbstractSyntaxTreeStatementsTest {

    private static final Locale locale = Locale.US;

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

        Object result = abs.run(arguments, locale, Configuration.DEFAULT);
        assertEquals("line 1\nline 2\nrandom text\ncondition was true\n1\n4\n9\n16", result);
    }

}
