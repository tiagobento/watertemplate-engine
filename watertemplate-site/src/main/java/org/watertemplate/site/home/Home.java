package org.watertemplate.site.home;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.menu.Menu;
import org.watertemplate.site.pages.tutorials.Documentation;
import org.watertemplate.site.pages.Examples;
import org.watertemplate.site.pages.tutorials.Installation;
import org.watertemplate.site.pages.tutorials.QuickStart;

public class Home extends Template {

    public static final String PATH = "/home";

    public Home() {
        add("quick_start_path", QuickStart.PATH);
        add("installation_path", Installation.PATH);
        add("documentation_path", Documentation.PATH);
        add("examples_path", Examples.PATH);
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


