package org.watertemplate.example.example.nestedtemplates;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;

public class MasterTemplate extends Template {

    public MasterTemplate(final String title) {
        add("title", title);
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("header", new Header());
        subTemplates.add("footer", new Footer());
    }

    @Override
    protected String getFilePath() {
        return "example/nestedtemplates/master_template.html";
    }
}
