package org.watertemplate.site.menu;

import org.watertemplate.Template;
import org.watertemplate.site.home.Home;
import org.watertemplate.site.pages.tutorials.Documentation;
import org.watertemplate.site.pages.Examples;
import org.watertemplate.site.pages.tutorials.Installation;
import org.watertemplate.site.pages.tutorials.QuickStart;

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
