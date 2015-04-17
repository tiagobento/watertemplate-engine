package org.watertemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateFixture {
    public static class MasterTemplate extends Template {
        protected String getFilePath() {
            return "masterTemplate.html";
        }
    }

    public static class SubTemplate extends Template {
        protected String getFilePath() {
            return "subTemplate.html";
        }
    }

    public static class TemplateOnlyWithMasterTemplate extends Template {
        @Override
        protected Template getMasterTemplate() {
            return new MasterTemplate();
        }

        @Override
        protected String getFilePath() {
            return "templateOnlyWithMasterTemplate.html";
        }
    }

    public static class TemplateOnlyWithSubTemplates extends Template {
        @Override
        protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
            subTemplates.add("sub_template", new SubTemplate());
        }

        @Override
        protected String getFilePath() {
            return "templateOnlyWithSubTemplates.html";
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
            return "templateWithMasterAndSubTemplates.html";
        }
    }

    public static class SubTemplateMasterTemplate extends Template {
        @Override
        protected String getFilePath() {
            return "subTemplateMasterTemplate.html";
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
            return "templateWithMasterTemplateAndSubTemplatesThatHaveMasterTemplate.html";
        }
    }

    public static class TemplateWithCollection extends Template {
        TemplateWithCollection(Integer ... integers) {
            List<String> map = Arrays.asList(integers).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            addCollection("items", map, null);
        }

        @Override
        protected String getFilePath() {
            return "templateWithCollection.html";
        }
    }
}

