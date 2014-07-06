package org.watertemplate.example.main;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.example.main.menu.MenuPartial;

public class ExampleMainTemplate extends Template {

    public ExampleMainTemplate() {
        add("title", "Example application using Water Templates");
    }

    @Override
    protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("header", new HeaderPartial());
        subTemplates.add("menu", new MenuPartial());
        subTemplates.add("footer", new FooterPartial());
    }

    @Override
    protected String getFilePath() {
        return "example/main.html.master";
    }
}
