package org.watertemplate.site.home;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.master.Master;
import org.watertemplate.site.menu.Menu;

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
        return "home/home.html";
    }
}


