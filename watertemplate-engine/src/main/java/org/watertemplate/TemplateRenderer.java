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

    public String renderWithMaster() {
        try {
            final Template masterTemplate = template.getMasterTemplate();

            if (masterTemplate == null) {
                return renderWithoutMaster();
            }

            masterTemplate.arguments.addTemplateWhichWontRenderItsMasterTemplate("content", template);
            return masterTemplate.render(locale);
        } catch (RuntimeException e) {
            throw new RenderException(template, locale, e);
        }
    }

    public String renderWithoutMaster() {
        TemplateMap.SubTemplates subTemplates = new TemplateMap.SubTemplates();
        template.addSubTemplates(subTemplates);
        subTemplates.map.forEach(template.arguments::add);

        String filePath = template.getFilePath();
        TemplateMap.Arguments arguments = template.arguments;
        Locale defaultLocale = template.getDefaultLocale();

        return new WaterInterpreter(filePath, arguments, defaultLocale).interpret(locale);
    }

}
