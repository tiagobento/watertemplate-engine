package org.watertemplate.site.pages.tutorials;

import org.watertemplate.TemplateMap;
import org.watertemplate.site.pages.examples.ExamplesList;

public class QuickStart extends Tutorial {
    public static final String PATH = "/quick-start";

    public QuickStart() {
        super("quick_start", 5);
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        super.addSubTemplates(subTemplates);
        subTemplates.add("examples_list", new ExamplesList());
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/quick_start/quick_start.html";
    }
}


