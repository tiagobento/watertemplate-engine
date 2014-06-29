package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TemplateMapTest {
    @Test
    public void add() {
        TemplateMap templateMap = new TemplateMap();
        templateMap.add("foo", "bar");

        Assert.assertEquals(1, templateMap.map.size());
        Assert.assertEquals("bar", templateMap.map.get("foo"));
    }

    @Test
    public void addCollection() {
        Collection<String> strings = Arrays.asList("foo", "bar");
        TemplateMap templateMap = new TemplateMap();

        templateMap.add("strings", strings, new TemplateMap.CollectionMapper<String>() {
            @Override
            public void map(final String string, final TemplateMap map) {
                map.add("lower", string.toLowerCase());
                map.add("upper", string.toUpperCase());
                map.add("size", string.length());
                map.add("chars", getChars(string), new TemplateMap.CollectionMapper<Character>() {
                    @Override
                    public void map(final Character character, final TemplateMap map) {
                        map.add("asciiCode", (int) character);
                    }
                });
            }
        });

        Assert.assertEquals(1, templateMap.map.size());
        Assert.assertTrue(templateMap.map.get("strings") instanceof TemplateMap.Collection);
    }

    private Collection<Character> getChars(final String string) {
        Collection<Character> chars = new ArrayList<>();

        for (int i = 0; i < string.length(); i++) {
            chars.add(string.charAt(i));
        }

        return chars;
    }
}
