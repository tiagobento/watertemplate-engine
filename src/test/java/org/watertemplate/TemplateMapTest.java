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
    public void addCollection() {
        Collection<String> strings = Arrays.asList("foo", "bar");
        TemplateMap.Arguments arguments = new TemplateMap.Arguments();

        arguments.add("strings", strings, (string, stringsMap) -> {
            stringsMap.add("lower", string.toLowerCase());
            stringsMap.add("upper", string.toUpperCase());
            stringsMap.add("size", string.length());
            stringsMap.add("chars", getChars(string), (character, charsMap) -> charsMap.add("asciiCode", (int) character));
        });

        Assert.assertEquals(1, arguments.map.size());
        Assert.assertTrue(arguments.map.get("strings") instanceof TemplateMap.TemplateCollection);
    }

    private Collection<Character> getChars(final String string) {
        Collection<Character> chars = new ArrayList<>();

        for (int i = 0; i < string.length(); i++) {
            chars.add(string.charAt(i));
        }

        return chars;
    }
}
