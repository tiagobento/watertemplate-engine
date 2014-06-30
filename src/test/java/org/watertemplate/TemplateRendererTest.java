package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class TemplateRendererTest {

    @Test
    public void templateOnlyWithMasterTemplate() {
        class TemplateOnlyWithMasterTemplate extends Template {
            @Override
            protected String getTemplateFilePath() {
                return "_template_only_with_master_template.st";
            }

            @Override
            protected Template getMasterTemplate() {
                return new Fixture.MasterTemplate();
            }
        }

        String rendered = render(new TemplateOnlyWithMasterTemplate());
        Assert.assertEquals("master_template_content\n" + "template_only_with_master_template_content", rendered);
    }

    @Test
    public void templateOnlyWithSubTemplates() {
        class TemplateOnlyWithSubTemplates extends Template {
            @Override
            protected String getTemplateFilePath() {
                return "_template_only_with_sub_templates.st";
            }

            @Override
            protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
                subTemplates.add("sub_template", new Fixture.SubTemplate());
            }
        }

        String rendered = render(new TemplateOnlyWithSubTemplates());
        Assert.assertEquals("template_only_with_sub_templates_content\n" + "sub_template_content", rendered);
    }

    @Test
    public void templateWithSubTemplatesAndMasterTemplate() {
        class TemplateWithSubTemplatesAndMasterTemplate extends Template {
            @Override
            protected String getTemplateFilePath() {
                return "_template_with_sub_templates_and_master_template.st";
            }

            @Override
            protected Template getMasterTemplate() {
                return new Fixture.MasterTemplate();
            }

            @Override
            protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
                subTemplates.add("sub_template", new Fixture.SubTemplate());
            }
        }

        String rendered = render(new TemplateWithSubTemplatesAndMasterTemplate());
        Assert.assertEquals("master_template_content\n" +
            "template_with_sub_templates_and_master_template_content\n" +
            "sub_template_content", rendered);
    }

    @Test
    public void templateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate() {
        class SubTemplateMasterTemplate extends Template {
            @Override
            protected String getTemplateFilePath() {
                return "_sub_template_master_template.st";
            }
        }

        class SubTemplateWithMasterTemplate extends Fixture.SubTemplate {
            @Override
            protected Template getMasterTemplate() {
                return new SubTemplateMasterTemplate();
            }
        }

        class TemplateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate extends Template {
            @Override
            protected String getTemplateFilePath() {
                return "_template_with_master_template_and_sub_templates_that_have_a_master_template.st";
            }

            @Override
            protected Template getMasterTemplate() {
                return new Fixture.MasterTemplate();
            }

            @Override
            protected void addSubTemplates(final TemplateMap.SubTemplates subTemplates) {
                subTemplates.add("sub_template", new SubTemplateWithMasterTemplate());
            }
        }

        String rendered = render(new TemplateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate());
        Assert.assertEquals("" +
                "master_template_content\n" +
                "template_that_have_sub_templates_that_have_a_master_template_content\n" +
                "sub_template_master_template_content\n" +
                "sub_template_content", rendered
        );
    }

    @Test(expected = TemplateException.class)
    public void templateWithInvalidTemplatePath() {
        render(new Template() {
            @Override
            protected String getTemplateFilePath() {
                return "_invalid.st";
            }
        });
    }

    @Test
    public void templateWithListArgs() {
        class TemplateWithListArgs extends Template {
            @Override
            protected String getTemplateFilePath() {
                return "_template_with_list_args.st";
            }

            TemplateWithListArgs() {
                add("items", new int[]{1, 2});
            }
        }

        String rendered = render(new TemplateWithListArgs());
        Assert.assertEquals("1\n2\n", rendered);
    }

    @Test
    public void templateWithNonexistentLocale() {
        Assert.assertEquals("sub_template_content", render(new Fixture.SubTemplate(), Locale.FRANCE));
    }

    private String render(final Template template) {
        return render(template, TemplateRenderer.DEFAULT_LOCALE);
    }

    private String render(final Template template, final Locale locale) {
        return new TemplateRenderer(template, locale).render();
    }
}
