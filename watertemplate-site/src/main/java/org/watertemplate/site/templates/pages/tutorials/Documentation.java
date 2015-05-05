package org.watertemplate.site.templates.pages.tutorials;

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
    public Master.Header getHeader() {
        return new Header();
    }

    private static class Header extends Master.Header {
        @Override
        String getHeaderContinuationPath() {
            return "pages/tutorials/documentation/title.html";
        }
    }
}
