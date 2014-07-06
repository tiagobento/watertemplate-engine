package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

public class TemplateTest {

    @Test
    public void render() {
        Template template = new Fixture.SubTemplate();
        Assert.assertNotNull(template.render());
    }
}
