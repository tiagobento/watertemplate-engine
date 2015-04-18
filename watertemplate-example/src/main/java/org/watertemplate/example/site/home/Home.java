package org.watertemplate.example.site.home;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.example.site.master.Master;
import org.watertemplate.example.site.menu.Menu;

public class Home extends Template {

    @Override
    protected Template getMasterTemplate() {
        return new Master();
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("menu", new Menu());
    }

    @Override
    protected String getFilePath() {
        return "site/home.html";
    }
}


