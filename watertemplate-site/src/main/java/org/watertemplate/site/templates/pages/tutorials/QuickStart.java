package org.watertemplate.site.templates.pages.tutorials;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;
import org.watertemplate.site.templates.pages.examples.ExamplesList;

public class QuickStart extends Tutorial {
    public static final String PATH = "/quick-start";

    public QuickStart() {
        super("quick_start", 5);
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        super.addSubTemplates(subTemplates);
        subTemplates.add("further_reading", new FurtherReading());
    }

    @Override
    protected String getFilePath() {
        return "pages/tutorials/quick_start/quick_start.html";
    }

    @Override
    public Master.Header getHeader() {
        return new Header();
    }

    //

    private static class FurtherReading extends Template {

        @Override
        protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
            subTemplates.add("examples_list", new ExamplesList());
        }

        @Override
        protected String getFilePath() {
            return "pages/tutorials/quick_start/further_reading.html";
        }
    }


    private static class Header extends Master.Header {
        @Override
        String getHeaderContinuationPath() {
            return "pages/tutorials/quick_start/title.html";
        }
    }
}


