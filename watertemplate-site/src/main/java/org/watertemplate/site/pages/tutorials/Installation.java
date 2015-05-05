package org.watertemplate.site.pages.tutorials;

public class Installation extends Tutorial {
    public static final String PATH = "/installation";

    public Installation() {
        super("installation", 3);
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/installation/installation.html";
    }
}
