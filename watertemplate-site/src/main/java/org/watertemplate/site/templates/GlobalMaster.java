package org.watertemplate.site.templates;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.templates.menu.Menu;

public class GlobalMaster extends Template {

    private final Template header;

    public GlobalMaster() {
        this.header = new Header();
    }

    public GlobalMaster(final Template header) {
        this.header = header;
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("header", header);
        subTemplates.add("menu", new Menu());
    }

    @Override
    protected String getFilePath() {
        return "master/master.html";
    }

    private static class Header extends Template {
        @Override
        protected String getFilePath() {
            return "master/header.html";
        }
    }
}
