package org.watertemplate.site.templates.pages.home;

import org.watertemplate.Template;
import org.watertemplate.site.templates.GlobalMaster;
import org.watertemplate.site.templates.pages.tutorials.Documentation;
import org.watertemplate.site.templates.pages.examples.Examples;
import org.watertemplate.site.templates.pages.tutorials.Installation;
import org.watertemplate.site.templates.pages.tutorials.QuickStart;

public class Home extends Template {

    public static final String PATH = "/home";

    public Home() {
        add("quick_start_path", QuickStart.PATH);
        add("installation_path", Installation.PATH);
        add("documentation_path", Documentation.PATH);
        add("examples_path", Examples.PATH);
    }

    @Override
    protected Template getMasterTemplate() {
        return new GlobalMaster(new Header());
    }

    @Override
    protected String getFilePath() {
        return "pages/home/home.html";
    }


    //


    private static class Header extends Template {
        @Override
        protected Template getMasterTemplate() {
            return new GlobalMaster.Header();
        }

        @Override
        protected String getFilePath() {
            return "pages/home/header.html";
        }
    }
}


