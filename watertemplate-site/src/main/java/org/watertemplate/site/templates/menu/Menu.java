package org.watertemplate.site.templates.menu;

import org.watertemplate.Template;
import org.watertemplate.site.templates.pages.home.Home;
import org.watertemplate.site.templates.pages.tutorials.Documentation;
import org.watertemplate.site.templates.pages.examples.Examples;
import org.watertemplate.site.templates.pages.tutorials.Installation;
import org.watertemplate.site.templates.pages.tutorials.QuickStart;

public class Menu extends Template {


    public Menu() {
        add("home_path", Home.PATH);
        add("installation_path", Installation.PATH);
        add("quick_start_path", QuickStart.PATH);
        add("documentation_path", Documentation.PATH);
        add("examples_path", Examples.PATH);
    }

    @Override
    protected String getFilePath() {
        return "menu/menu.html";
    }
}
