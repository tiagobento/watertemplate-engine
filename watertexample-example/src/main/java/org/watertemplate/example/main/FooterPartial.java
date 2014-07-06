package org.watertemplate.example.main;

import org.watertemplate.Template;

class FooterPartial extends Template {
    @Override
    protected String getFilePath() {
        return "example/footer.html.partial";
    }
}
