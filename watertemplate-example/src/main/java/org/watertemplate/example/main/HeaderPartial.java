package org.watertemplate.example.main;

import org.watertemplate.Template;

class HeaderPartial extends Template {
    @Override
    protected String getFilePath() {
        return "example/header.html.partial";
    }
}
