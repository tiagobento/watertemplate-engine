package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TemplateMapTest {
    @Test
    public void add() {
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.add("foo", "bar");

        Assert.assertEquals(1, arguments.map.size());
        Assert.assertEquals("bar", arguments.map.get("foo"));
    }

    @Test
    public void addMappedObject() {
        String key = "string";
        String value = "aString";

        TemplateMap.Arguments arguments = new TemplateMap.Arguments();
        arguments.addMappedObject(key, value, (string, stringMap) -> {
            stringMap.add("lower", string.toLowerCase());
            stringMap.add("upper", string.toUpperCase());
        });

        Assert.assertEquals(1, arguments.map.size());
        Assert.assertEquals(value, arguments.get(key));
        Assert.assertTrue(arguments.map.get(key) instanceof TemplateMap.TemplateObject);
    }


    @Test
    public void addCollection() {
        Collection<String> strings = Arrays.asList("foo", "bar");
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();

        arguments.addCollection("strings", strings, (string, stringMap) -> {
            stringMap.add("lower", string.toLowerCase());
            stringMap.add("upper", string.toUpperCase());
            stringMap.add("size", string.length());

            stringMap.addCollection("chars", getChars(string), (character, charsMap) ->
                    charsMap.add("asciiCode", (int) character)
            );
        });

        Assert.assertEquals(1, arguments.map.size());
        Assert.assertTrue(arguments.map.get("strings") instanceof TemplateMap.TemplateCollection);
    }

    @Test
    public void map() {
        final TemplateMap.TemplateObject<String> templateObject;
        templateObject = new TemplateMap.TemplateObject<>("foo", (string, stringMap) -> {
            stringMap.add("lower", string.toLowerCase());
            stringMap.add("upper", string.toUpperCase());
            stringMap.add("size", string.length());
        });

        final TemplateMap.Arguments map = templateObject.map();
        Assert.assertEquals(map.get("lower"), "foo");
        Assert.assertEquals(map.get("upper"), "FOO");
        Assert.assertEquals(map.get("size"), "3");

    }

    private Collection<Character> getChars(final String string) {
        Collection<Character> chars = new ArrayList<>();

        for (int i = 0; i < string.length(); i++) {
            chars.add(string.charAt(i));
        }

        return chars;
    }
}
