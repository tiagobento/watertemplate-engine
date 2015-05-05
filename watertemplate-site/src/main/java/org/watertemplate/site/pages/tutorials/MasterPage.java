package org.watertemplate.site.pages.tutorials;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.menu.Menu;

class MasterPage extends Template {


    @Override
    protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("menu", new Menu());
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/master.html";
    }
}
