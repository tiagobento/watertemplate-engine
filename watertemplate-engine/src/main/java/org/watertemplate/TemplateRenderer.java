package org.watertemplate;

import org.watertemplate.exception.RenderException;
import org.watertemplate.interpreter.WaterInterpreter;

import java.util.Locale;

class TemplateRenderer {

    private final Template template;
    private final Locale locale;

    public TemplateRenderer(final Template template, final Locale locale) {
        this.template = template;
        this.locale = locale;
    }

    public String render() {
        try {
            final String renderedTemplateWithRenderedSubTemplates = renderTemplateWithSubTemplates();
            return renderMasterTemplateIfNecessary(renderedTemplateWithRenderedSubTemplates);
        } catch (RuntimeException e) {
            throw new RenderException(template, locale, e);
        }
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
        addSubTemplatesAsArguments();
        return renderTemplate();
    }

    private void addSubTemplatesAsArguments() {
        TemplateMap.SubTemplates subTemplates = new TemplateMap.SubTemplates();
        template.addSubTemplates(subTemplates);
        subTemplates.map.forEach(template.arguments::add);
    }

    private String renderTemplate() {
        String filePath = template.getFilePath();
        TemplateMap.Arguments arguments = template.arguments;
        Locale defaultLocale = template.getDefaultLocale();

        return new WaterInterpreter(filePath, arguments, defaultLocale).interpret(locale);
    }

}
