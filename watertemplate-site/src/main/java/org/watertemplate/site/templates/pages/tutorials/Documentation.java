package org.watertemplate.site.templates.pages.tutorials;

import org.watertemplate.Template;

public class Documentation extends Tutorial {
    public static final String PATH = "/documentation";

    public Documentation() {
        super("documentation", 6);
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/documentation/documentation.html";
    }

    @Override
    public Template getHeader() {
        return new Header();
    }


    //


    private static class Header extends Template {
        @Override
        protected Template getMasterTemplate() {
            return new Master.Header();
        }

        @Override
        public String getFilePath() {
            return "pages/tutorials/documentation/header.html";
        }
    }
}
