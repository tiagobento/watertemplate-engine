package org.watertemplate.example.nestedtemplates;

import org.watertemplate.Template;

class Header extends Template {
    @Override
    protected String getFilePath() {
        return "example/nestedtemplates/header.html";
    }
}
