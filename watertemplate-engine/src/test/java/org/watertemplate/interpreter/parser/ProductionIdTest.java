package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.TemplateMap;
import org.watertemplate.interpreter.parser.abs.AbstractSyntaxTree;

import static org.junit.Assert.assertEquals;
import static org.watertemplate.interpreter.parser.Terminal.ACCESSOR;
import static org.watertemplate.interpreter.parser.Terminal.PROPERTY_KEY;

public class ProductionIdTest {
    @Test
    public void withTwoNestedProperties() {
        Production.IdWithNestedProperties idWithNestedProperties = new Production.IdWithNestedProperties();

        ParseTree parseTree = new ParseTree(idWithNestedProperties).withChildren(
                new ParseTree(PROPERTY_KEY, "char"),
                new ParseTree(ACCESSOR),
                new ParseTree(idWithNestedProperties).withChildren(
                        new ParseTree(PROPERTY_KEY, "upperCase"),
                        new ParseTree(ACCESSOR),
                        new ParseTree(PROPERTY_KEY, "withExclamationMark")
                )
        );

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.addMappedObject("char", "a", (character, map) -> {
            map.addMappedObject("upperCase", character.toUpperCase(), (upperCase, upMap) -> {
                upMap.add("withExclamationMark", upperCase + "!");
            });
        });

        AbstractSyntaxTree abstractSyntaxTree = idWithNestedProperties.buildAbstractSyntaxTree(parseTree);
        String result = (String) abstractSyntaxTree.run(arguments);
        assertEquals("A!", result);
    }

    @Test
    public void onlyOnePropertyName() {
        ParseTree parseTree = new ParseTree(PROPERTY_KEY, "char");

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.add("char", "a");

        AbstractSyntaxTree abs = PROPERTY_KEY.buildAbstractSyntaxTree(parseTree);
        Object result = abs.run(arguments);
        assertEquals("a", result);
    }
}
