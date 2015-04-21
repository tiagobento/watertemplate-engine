package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.exception.TemplateException;

import java.util.Locale;

public class TemplateRendererTest {

    @Test
    public void templateOnlyWithMasterTemplate() {
        final Template template = new TemplateFixture.TemplateOnlyWithMasterTemplate();
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("master_template_content\n" + "template_only_with_master_template_content", rendered);
    }

    @Test
    public void templateOnlyWithSubTemplates() {
        final Template template = new TemplateFixture.TemplateOnlyWithSubTemplates();
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("template_only_with_sub_templates_content\n" + "sub_template_content", rendered);
    }

    @Test
    public void templateWithSubTemplatesAndMasterTemplate() {
        final Template template = new TemplateFixture.TemplateWithSubTemplatesAndMasterTemplate();
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("master_template_content\n" +
                "template_with_sub_templates_and_master_template_content\n" +
                "sub_template_content", rendered);
    }

    @Test
    public void templateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate() {
        final Template template = new TemplateFixture.TemplateWithMasterTemplateAndSubTemplatesThatHaveAMasterTemplate();
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("" +
                        "master_template_content\n" +
                        "template_that_have_sub_templates_that_have_a_master_template_content\n" +
                        "sub_template_master_template_content\n" +
                        "sub_template_content", rendered
        );
    }

    @Test
    public void templateWithCollection() {
        final Template template = new TemplateFixture.TemplateWithCollection(1, 2, 3, 4);
        String rendered = template.render(template.getDefaultLocale());
        Assert.assertEquals("1\n2\n3\n4\n", rendered);
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
    public void lazySubTemplates() {
        class LazySubTemplate extends Template {
            int timesRendered = 0;

            @Override
            protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
                // each time the renderer renders a template this method is called once.
                timesRendered++;
            }

            @Override
            protected String getFilePath() {
                return "lazy_sub_templates/sub_template.html";
            }
        }

        class LazyTemplate extends Template {
            final LazySubTemplate subTemplate = new LazySubTemplate();

            LazyTemplate(Boolean renderSubTemplate) {
                add("render_sub_templates", renderSubTemplate);
            }

            @Override
            protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
                subTemplates.add("sub_template", subTemplate);
            }

            @Override
            protected String getFilePath() {
                return "lazy_sub_templates/template.html";
            }
        }

        LazyTemplate lazyTemplate = new LazyTemplate(false);
        Assert.assertEquals("", lazyTemplate.render());
        Assert.assertEquals(0, lazyTemplate.subTemplate.timesRendered);

        lazyTemplate = new LazyTemplate(true);
        Assert.assertEquals("rendered", lazyTemplate.render());
        Assert.assertEquals(1, lazyTemplate.subTemplate.timesRendered);

        Assert.assertEquals("rendered", lazyTemplate.render());
        Assert.assertEquals(2, lazyTemplate.subTemplate.timesRendered);
    }

    @Test
    public void templateWithNonexistentLocale() {
        Assert.assertEquals("sub_template_content", new TemplateFixture.SubTemplate().render(Locale.FRANCE));
    }
}
