package org.watertemplate;

import org.watertemplate.parser.STParser;

import java.io.File;
import java.io.FileNotFoundException;
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

    public String render() {
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

    private String renderTemplateWithSubTemplates() {
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

    private String renderTemplate() {
        return new STParser(getTemplateFileURI(), template.arguments.map).parse();
    }

    //

    private String getTemplateFileURI() {
        return getTemplateFileURI(locale);
    }

    private String getTemplateFileURI(final Locale locale) {
        final String templateFileURI = "templates" + File.separator + locale + File.separator + template.getTemplateFilePath();
        final URL resource = getClass().getClassLoader().getResource(templateFileURI);

        if (resource != null) {
            return resource.getFile();
        } else if (!locale.equals(DEFAULT_LOCALE)) {
            return getTemplateFileURI(DEFAULT_LOCALE);
        } else {
            throw new TemplateException(new FileNotFoundException(templateFileURI));
        }
    }
}
