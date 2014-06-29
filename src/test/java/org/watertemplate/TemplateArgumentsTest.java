package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

public class TemplateArgumentsTest {
    @Test
    public void add() {
        TemplateArguments templateArguments = new TemplateArguments();
        templateArguments.add("foo", "bar");

        Assert.assertEquals(1, templateArguments.map.size());
        Assert.assertEquals("bar", templateArguments.map.get("foo"));
    }
}
