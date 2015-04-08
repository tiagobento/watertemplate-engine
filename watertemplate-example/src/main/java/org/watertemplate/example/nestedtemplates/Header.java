package org.watertemplate.example.nestedtemplates;

import org.watertemplate.Template;

public class Header extends Template {
    @Override
    protected String getFilePath() {
        return "nestedtemplates/header.html";
    }
}
