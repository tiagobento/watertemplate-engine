package org.watertemplate.site.templates.pages.tutorials;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.templates.menu.Menu;
import org.watertemplate.site.templates.WaterSiteMasterPage;

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
        return new WaterSiteMasterPage(this.header);
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/master.html";
    }






    //


    private static class DefaultHeader extends Header {

        @Override
        String getTitlePath() {
            return "pages/tutorials/title.html";
        }
    }






    //


    static abstract class Header extends Template {

        abstract String getTitlePath();

        @Override
        protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
            subTemplates.add("title", new Template() {
                @Override
                protected String getFilePath() {
                    return getTitlePath();
                }
            });
        }

        @Override
        protected String getFilePath() {
            return "pages/tutorials/header.html";
        }
    }
}
