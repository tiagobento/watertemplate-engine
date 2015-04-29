package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.exception.TemplateException;

import java.util.Locale;

public class NestedTemplatesTest {

    @Test
    public void templateOnlyWithMasterTemplate() {
        final Template template = new NestedTemplatesFixture.TemplateOnlyWithMasterTemplate();
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("master_template_content\n" + "template_only_with_master_template_content", rendered);
    }

    @Test
    public void templateOnlyWithSubTemplates() {
        final Template template = new NestedTemplatesFixture.TemplateOnlyWithSubTemplates();
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("template_only_with_sub_templates_content\n" + "sub_template_content", rendered);
    }

    @Test
    public void templateWithSubTemplatesAndMasterTemplate() {
        final Template template = new NestedTemplatesFixture.TemplateWithSubTemplatesAndMasterTemplate();
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("master_template_content\n" +
                "template_with_sub_templates_and_master_template_content\n" +
                "sub_template_content", rendered);
    }

    @Test
    public void templateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate() {
        final Template template = new NestedTemplatesFixture.TemplateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate();
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("" +
                        "master_template_content\n" +
                        "template_that_have_sub_templates_that_have_a_master_template_content\n" +
                        "sub_template_master_template_content\n" +
                        "sub_template_content", rendered
        );
    }

    @Test(expected = TemplateException.class)
    public void templateWithInvalidFilePath() {
        final Template template = new Template() {
            @Override
            protected String getFilePath() {
                return "invalid.html";
            }
        };
        template.render(template.getDefaultLocale());
    }

    @Test
    public void templateWithNonexistentLocale() {
        Assert.assertEquals("sub_template_content", new NestedTemplatesFixture.SubTemplate().render(Locale.FRANCE));
    }
}
