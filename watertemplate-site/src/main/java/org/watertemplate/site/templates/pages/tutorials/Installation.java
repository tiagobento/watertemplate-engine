package org.watertemplate.site.templates.pages.tutorials;

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
    public Master.Header getHeader() {
        return new Header();
    }

    private static class Header extends Master.Header {
        @Override
        String getHeaderContinuationPath() {
            return "pages/tutorials/installation/title.html";
        }
    }
}
