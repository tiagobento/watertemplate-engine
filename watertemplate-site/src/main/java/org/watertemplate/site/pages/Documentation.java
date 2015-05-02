package org.watertemplate.site.pages;

import org.watertemplate.Template;

public class Documentation extends Template {
    public static final String PATH = "/documentation";

    @Override
    protected String getFilePath() {
        return "pages/documentation.html";
    }
}
