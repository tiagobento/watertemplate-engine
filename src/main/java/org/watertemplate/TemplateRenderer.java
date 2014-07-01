package org.watertemplate;

import org.watertemplate.parser.STParser;

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
        return new STParser(template.getFilePath(), template.arguments.map).parse(locale);
    }
}
