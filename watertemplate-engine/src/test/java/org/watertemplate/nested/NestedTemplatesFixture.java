package org.watertemplate.nested;

import org.watertemplate.Template;
import org.watertemplate.TemplateMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NestedTemplatesFixture {
    public static class MasterTemplate extends Template {
        protected String getFilePath() {
            return "nested/masterTemplate.html";
        }
    }

    public static class SubTemplate extends Template {
        protected String getFilePath() {
            return "nested/subTemplate.html";
        }
    }

    public static class TemplateOnlyWithMasterTemplate extends Template {
        @Override
        protected Template getMasterTemplate() {
            return new MasterTemplate();
        }

        @Override
        protected String getFilePath() {
            return "nested/templateOnlyWithMasterTemplate.html";
        }
    }

    public static class TemplateOnlyWithSubTemplates extends Template {
        @Override
        protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
            subTemplates.add("sub_template", new SubTemplate());
        }

        @Override
        protected String getFilePath() {
            return "nested/templateOnlyWithSubTemplates.html";
        }
    }

    public static class TemplateWithSubTemplatesAndMasterTemplate extends Template {
        @Override
        protected Template getMasterTemplate() {
            return new MasterTemplate();
        }

        @Override
        protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
            subTemplates.add("sub_template", new SubTemplate());
        }

        @Override
        protected String getFilePath() {
            return "nested/templateWithMasterAndSubTemplates.html";
        }
    }

    public static class SubTemplateMasterTemplate extends Template {
        @Override
        protected String getFilePath() {
            return "nested/subTemplateMasterTemplate.html";
        }
    }

    public static class SubTemplateWithMasterTemplate extends SubTemplate {
        @Override
        protected Template getMasterTemplate() {
            return new SubTemplateMasterTemplate();
        }
    }

    static class TemplateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate extends Template {
        @Override
        protected Template getMasterTemplate() {
            return new MasterTemplate();
        }

        @Override
        protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
            subTemplates.add("sub_template", new SubTemplateWithMasterTemplate());
        }

        @Override
        protected String getFilePath() {
            return "nested/templateWithMasterTemplateAndSubTemplatesThatHaveMasterTemplate.html";
        }
    }
}

