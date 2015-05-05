package org.watertemplate.site.pages.tutorials;

public class Documentation extends Tutorial {
    public static final String PATH = "/documentation";

    public Documentation() {
        super("documentation", 6);
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/documentation/documentation.html";
    }
}
