package org.watertemplate.site.home;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.menu.Menu;
import org.watertemplate.site.pages.Documentation;
import org.watertemplate.site.pages.Examples;
import org.watertemplate.site.pages.Installation;
import org.watertemplate.site.pages.QuickStart;

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

    public static void main(String[] args) {
        System.out.println(new Home().render());
    }
}


