package org.watertemplate.example.nestedtemplates;

import org.watertemplate.Template;

class HomePage extends Template {

    public HomePage() {
    }

    @Override
    protected Template getMasterTemplate() {
        return new MasterTemplate();
    }

    @Override
    protected String getFilePath() {
        return "nestedtemplates/homepage.html";
    }
}
