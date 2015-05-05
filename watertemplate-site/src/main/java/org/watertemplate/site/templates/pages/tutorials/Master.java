package org.watertemplate.site.templates.pages.tutorials;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.templates.GlobalMaster;

class Master extends Template {

    private final Header header;

    Master() {
        this.header = new DefaultHeader();
    }

    Master(final Header header) {
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


    private static class DefaultHeader extends Header {

        @Override
        String getHeaderContinuationPath() {
            return "pages/tutorials/title.html";
        }
    }






    //


    static abstract class Header extends Template {

        abstract String getHeaderContinuationPath();

        @Override
        protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
            subTemplates.add("header_continuation", new Template() {
                @Override
                protected String getFilePath() {
                    return getHeaderContinuationPath();
                }
            });
        }

        @Override
        protected String getFilePath() {
            return "pages/tutorials/header.html";
        }
    }
}
