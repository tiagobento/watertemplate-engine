package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.exception.TemplateException;

import java.util.Locale;

public class TemplateRendererTest {

    @Test
    public void templateOnlyWithMasterTemplate() {
        String rendered = render(new TemplateFixture.TemplateOnlyWithMasterTemplate());
        Assert.assertEquals("master_template_content\n" + "template_only_with_master_template_content", rendered);
    }

    @Test
    public void templateOnlyWithSubTemplates() {
        String rendered = render(new TemplateFixture.TemplateOnlyWithSubTemplates());
        Assert.assertEquals("template_only_with_sub_templates_content\n" + "sub_template_content", rendered);
    }

    @Test
    public void templateWithSubTemplatesAndMasterTemplate() {
        String rendered = render(new TemplateFixture.TemplateWithSubTemplatesAndMasterTemplate());
        Assert.assertEquals("master_template_content\n" +
            "template_with_sub_templates_and_master_template_content\n" +
            "sub_template_content", rendered);
    }

    @Test
    public void templateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate() {
        String rendered = render(new TemplateFixture.TemplateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate());
        Assert.assertEquals("" +
                "master_template_content\n" +
                "template_that_have_sub_templates_that_have_a_master_template_content\n" +
                "sub_template_master_template_content\n" +
                "sub_template_content", rendered
        );
    }

    @Test
    public void templateWithCollection() {
        String rendered = render(new TemplateFixture.TemplateWithCollection(1,2,3,4));
        Assert.assertEquals("1\n2\n3\n4\n", rendered);
    }

    @Test(expected = TemplateException.class)
    public void templateWithInvalidFilePath() {
        render(new Template() {
            @Override
            protected String getFilePath() {
                return "invalid.html";
            }
        });
    }

    @Test
    public void templateWithNonexistentLocale() {
        Assert.assertEquals("sub_template_content", render(new TemplateFixture.SubTemplate(), Locale.FRANCE));
    }

    private String render(final Template template) {
        return render(template, template.getDefaultLocale());
    }

    private String render(final Template template, final Locale locale) {
        return new TemplateRenderer(template, locale).render();
    }
}
