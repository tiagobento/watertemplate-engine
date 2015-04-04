package org.watertemplate.interpreter.parser.abs;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.watertemplate.TemplateMap.Arguments;

public class StatementsCommandTest {

    @Test
    public void sequentialStatements() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree(
                new StatementsCommand(
                        new IdCommand("x"),
                        new TextCommand("\n"),
                        new IdCommand("y"),
                        new TextCommand("\nrandom text\n"),
                        new IfCommand(new IdCommand("condition"),
                                new TextCommand("condition was true")
                        ),
                        new ForCommand("i", new IdCommand("collection"),
                                new StatementsCommand(
                                        new TextCommand("\n"),
                                        new IdCommand("i", new IdCommand("square"))
                                )
                        )
                ));

        Arguments arguments = new Arguments();
        arguments.add("x", "line 1");
        arguments.add("y", "line 2");
        arguments.add("condition", true);
        arguments.addCollection("collection", Arrays.asList(1, 2, 3, 4), (i, map) -> {
            map.add("square", Math.round(Math.pow(i, 2)));
        });

        String result = abs.run(arguments);
        assertEquals("line 1\nline 2\nrandom text\ncondition was true\n1\n4\n9\n16", result);
    }

}