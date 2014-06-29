package com.highlight.template;

import com.highlight.template.parser.STParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

class TemplateRenderer {

    static final Locale DEFAULT_LOCALE = Locale.US;

    private final Template template;
    private final Locale locale;

    public TemplateRenderer(final Template template, final Locale locale) {
        this.template = template;
        this.locale = locale;
    }

    public String render() throws IOException {
        final String renderedTemplateWithRenderedSubTemplates = renderTemplateWithSubTemplates();
        return renderMasterTemplateIfNecessary(renderedTemplateWithRenderedSubTemplates);
    }

    private String renderMasterTemplateIfNecessary(final String renderedTemplateWithoutMasterTemplate) {
        final Template masterTemplate = template.getMasterTemplate();

        if (masterTemplate == null) {
            return renderedTemplateWithoutMasterTemplate;
        }

        masterTemplate.add("content", renderedTemplateWithoutMasterTemplate);
        return masterTemplate.render(locale);
    }

    private String renderTemplateWithSubTemplates() throws IOException {
        renderSubTemplatesAddingThemAsTemplateArguments();
        return renderTemplate();
    }

    private void renderSubTemplatesAddingThemAsTemplateArguments() {
        for (final Map.Entry<String, Template> subTemplate : template.getSubTemplates().entrySet())
            template.add(
                subTemplate.getKey(),
                subTemplate.getValue().render(locale)
            );
    }

    private String renderTemplate() throws IOException {
        return new STParser(getTemplateAsString(), template.args).parse();
    }

    //

    private String getTemplateAsString() throws IOException {
        return getTemplateAsString(locale);
    }

    private String getTemplateAsString(final Locale locale) throws IOException {
        final String templatePath = "templates" + File.separator + locale + File.separator + template.getTemplateFileURI();
        final URL resource = getClass().getClassLoader().getResource(templatePath);

        if (resource != null) {
            return FileUtils.readFileToString(new File(resource.getFile()));
        } else if (!locale.equals(DEFAULT_LOCALE)) {
            return getTemplateAsString(DEFAULT_LOCALE);
        } else {
            throw new FileNotFoundException(templatePath);
        }
    }
}
