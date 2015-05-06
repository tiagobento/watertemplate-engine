package org.watertemplate.site.templates.pages.tutorials;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.templates.GlobalMaster;

class Master extends Template {

    private final Template header;

    Master(final Template header) {
        this.header = header;
    }

    @Override
    protected Template getMasterTemplate() {
        return new GlobalMaster(this.header);
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/master.html";
    }


    //


    static final class Header extends Template {
        @Override
        protected String getFilePath() {
            return "pages/tutorials/header.html";
        }

        @Override
        protected Template getMasterTemplate() {
            return new GlobalMaster.Header();
        }
    }
}
