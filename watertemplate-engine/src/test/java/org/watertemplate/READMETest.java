package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class READMETest {

    static class READMETemplate extends Template {

        READMETemplate() {
            add("title", "Full syntax");
            add("mustDisplayMenu", true);

            List<String[]> menuItems = new ArrayList<>();
            menuItems.add(new String[]{"Home", "/"});
            menuItems.add(new String[]{"About", "/about"});
            menuItems.add(new String[]{"Contact", "/contact"});

            addCollection("menuItems", menuItems, (item, map) -> {
                map.add("label", item[0]);
                map.add("url", item[1]);
            });
        }

        @Override
        protected String getFilePath() {
            return "READMETemplate.html";
        }
    }

    @Test
    public void testFullSyntaxTemplate() {
        String result = new READMETemplate().render();

        Assert.assertTrue(result.contains("Full syntax"));
        Assert.assertTrue(result.contains("Home"));
        Assert.assertTrue(result.contains("About"));
        Assert.assertTrue(result.contains("Contact"));
    }
}
