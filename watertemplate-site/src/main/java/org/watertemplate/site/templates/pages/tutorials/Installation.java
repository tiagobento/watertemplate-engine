package org.watertemplate.site.templates.pages.tutorials;

import org.watertemplate.Template;

public class Installation extends Tutorial {
    public static final String PATH = "/installation";

    public Installation() {
        super("installation", 3);
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/installation/installation.html";
    }

    @Override
    public org.watertemplate.Template getHeader() {
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
            return "pages/tutorials/installation/header.html";
        }
    }
}
