package org.watertemplate.example.site.master;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;

public class Master extends Template {

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("header", new Header());
        subTemplates.add("footer", new Footer());
    }



    @Override
    protected String getFilePath() {
        return "site/master.html";
    }
}
