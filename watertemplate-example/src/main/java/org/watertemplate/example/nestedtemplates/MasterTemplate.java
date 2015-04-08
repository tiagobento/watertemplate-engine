package org.watertemplate.example.nestedtemplates;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;

public class MasterTemplate extends Template {
    @Override
    protected String getFilePath() {
        return "nestedtemplates/master_template.html";
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("header", new Header());
        subTemplates.add("footer", new Footer());
    }
}
