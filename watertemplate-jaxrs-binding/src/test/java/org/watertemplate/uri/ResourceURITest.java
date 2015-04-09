package org.watertemplate.uri;

import org.junit.Assert;
import org.junit.Test;
import org.watertemplate.exception.TemplateException;

public class ResourceURITest {

    @Test
    public void formatPath() {
        String formattedPath = ResourceURI.format("/{id}/{otherId}", 1L, 2L);
        Assert.assertEquals("/1/2", formattedPath);
    }

    @Test(expected = TemplateException.class)
    public void formatPathWithNotEnoughArgs() {
        ResourceURI.format("/{id}/{otherId}", 1L);
    }
}
