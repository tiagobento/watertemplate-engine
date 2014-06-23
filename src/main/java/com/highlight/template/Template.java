package com.highlight.template;

import org.apache.commons.io.FileUtils;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.ST;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class Template {

    private static final Locale DEFAULT_LOCALE = Locale.US;

    /* Please use me */
    protected final Map<String, Object> args = new HashMap<>();

    /* Please override me */
    protected Map<String, Template> getSubTemplates() {
        return new HashMap<>();
    }

    /* Please override me */
    protected Template getMasterTemplate() {
        return null;
    }

    /* Implement me (you have no choice) */
    protected abstract String getTemplateFileURI();

    //

    public final String build() {
        return build(DEFAULT_LOCALE);
    }

    public final String build(final Locale locale) {
        try {
            final String renderedSelf = renderSelfAndSubTemplates(locale);
            return renderMasterTemplateIfNecessary(renderedSelf);
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    private String renderMasterTemplateIfNecessary(final String renderedThis) {
        final Template masterTemplate = getMasterTemplate();
        if (masterTemplate != null) {
            masterTemplate.args.put("content", renderedThis);
            return masterTemplate.build();
        } else {
            return renderedThis;
        }
    }

    private String renderSelfAndSubTemplates(final Locale locale) throws IOException {
        for (Map.Entry<String, Template> entry : getSubTemplates().entrySet())
            args.put(entry.getKey(), entry.getValue().build(locale));

        final ST st = createStringTemplateRenderer(locale);

        for (Map.Entry<String, Object> entry : args.entrySet())
            st.add(entry.getKey(), entry.getValue());

        return st.render(locale);
    }

    private ST createStringTemplateRenderer(final Locale locale) throws IOException {
        final ST st = new ST(getTemplateAsString(locale), '~', '~');
        st.groupThatCreatedThisInstance.registerRenderer(Date.class, new DateAttributeRenderer());
        return st;
    }

    private String getTemplateAsString(final Locale locale) throws IOException {
        final String templatePath = "templates" + File.separator + locale + File.separator + getTemplateFileURI();
        final URL resource = getClass().getClassLoader().getResource(templatePath);

        if (resource != null) {
            return FileUtils.readFileToString(new File(resource.getFile()));
        } else if (!locale.equals(DEFAULT_LOCALE)) {
            return getTemplateAsString(DEFAULT_LOCALE);
        } else {
            throw new FileNotFoundException(templatePath);
        }
    }

    private static class DateAttributeRenderer implements AttributeRenderer {
        @Override
        public String toString(Object o, String s, Locale locale) {
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            return dateFormat.format((Date) o);
        }

    }
}
