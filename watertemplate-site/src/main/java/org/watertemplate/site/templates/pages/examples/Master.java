package org.watertemplate.site.templates.pages.examples;

import org.watertemplate.Template;
import org.watertemplate.site.templates.WaterSiteMasterPage;

class Master extends Template {
    @Override
    protected Template getMasterTemplate() {
        return new WaterSiteMasterPage();
    }

    @Override
    protected String getFilePath() {
        return "pages/examples/master.html";
    }
}
