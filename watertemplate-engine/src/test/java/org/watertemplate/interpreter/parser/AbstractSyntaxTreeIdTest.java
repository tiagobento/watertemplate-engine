package org.watertemplate.interpreter.parser;

import org.junit.Test;
import org.watertemplate.TemplateMap;
import org.watertemplate.interpreter.parser.exception.IdCouldNotBeResolvedException;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;

public class AbstractSyntaxTreeIdTest {

    private static final Locale locale = Locale.US;

    @Test
    public void idWithOnlyOnePropertyKey() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        AbstractSyntaxTree abs = new AbstractSyntaxTree.Id("prop_key");

        arguments.add("prop_key", "success");

        Object result = abs.string(arguments, locale);
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

        Object result = abs.string(arguments, locale);
        assertEquals("success", result);
    }

    @Test
    public void localeSensitiveObject() {
        final Date now = new Date();
        final BiFunction<Date, Locale, String> localeFormatter = (date, locale) -> DateFormat.getDateInstance(DateFormat.FULL, locale).format(date);

        AbstractSyntaxTree abs = new AbstractSyntaxTree.Id("now");

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.addLocaleSensitiveObject("now", now, localeFormatter);

        Object americanDate = abs.string(arguments, Locale.US);
        assertEquals(localeFormatter.apply(now, Locale.US), americanDate);

        Object germanDate = abs.string(arguments, Locale.GERMAN);
        assertEquals(localeFormatter.apply(now, Locale.GERMAN), germanDate);
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

        arguments.addMappedObject("prop_key", "success", (x, map) -> {
            map.add("nested1", "foo");
        });
        abs.string(arguments, locale);
    }

    @Test(expected = IdCouldNotBeResolvedException.class)
    public void propertyNotPresentInArguments() {
        AbstractSyntaxTree abs = new AbstractSyntaxTree.Id("prop_key");

        Object result = abs.string(new TemplateMap.Arguments(), locale);
        assertEquals("prop_key", result);
    }
}
