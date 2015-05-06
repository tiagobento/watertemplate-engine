package org.watertemplate.site.templates.pages.examples;

import org.watertemplate.Template;
import org.watertemplate.site.templates.GlobalMaster;

class Master extends Template {
    private Template header;

    Master(final Template header) {
        this.header = header;
    }

    @Override
    protected Template getMasterTemplate() {
        return new GlobalMaster(this.header);
    }

    @Override
    protected String getFilePath() {
        return "pages/examples/master.html";
    }
}
