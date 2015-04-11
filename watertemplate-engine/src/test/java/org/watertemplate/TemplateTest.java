package org.watertemplate;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TemplateTest {

    @Test
    public void render() {
        Template template = new TemplateFixture.SubTemplate();
        Assert.assertNotNull(template.render());
    }
}
