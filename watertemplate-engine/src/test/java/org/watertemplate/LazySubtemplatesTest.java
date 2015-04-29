package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.Template;
import org.watertemplate.TemplateMap;

public class LazySubtemplatesTest {

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
        Assert.assertEquals("\nrendered\n", lazyTemplate.render());
        Assert.assertEquals(1, lazyTemplate.subTemplate.timesRendered);

        Assert.assertEquals("\nrendered\n", lazyTemplate.render());
        Assert.assertEquals(2, lazyTemplate.subTemplate.timesRendered);
    }
}
