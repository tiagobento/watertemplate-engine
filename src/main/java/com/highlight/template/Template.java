package com.highlight.template;

import org.apache.commons.io.FileUtils;
import org.stringtemplate.v4.ST;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class Template {

    static final Locale DEFAULT_LOCALE = Locale.US;

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

    final String build() {
        return build(DEFAULT_LOCALE);
    }

    final String build(final Locale locale) {
        try {
            if (StaticTemplatesCache.contains(getClass(), locale)) {
                return StaticTemplatesCache.get(getClass(), locale);
            } else {
                return fullyRenderSelfAndCacheIfNecessary(locale);
            }
        } catch (Exception e) {
            throw new TemplateException(e);
        }
    }

    private String fullyRenderSelfAndCacheIfNecessary(final Locale locale) throws IOException {
        final String selfWithSubTemplates = renderSelfWithSubTemplates(locale);
        final String fullyRenderedSelf = renderMasterTemplateIfNecessary(selfWithSubTemplates, locale);

        StaticTemplatesCache.cacheIfNecessary(getClass(), locale, fullyRenderedSelf);

        return fullyRenderedSelf;
    }

    private String renderMasterTemplateIfNecessary(final String partiallyRenderedSelf, final Locale locale) {
        final Template masterTemplate = getMasterTemplate();
        if (masterTemplate != null) {
            masterTemplate.args.put("content", partiallyRenderedSelf);
            return masterTemplate.build(locale);
        } else {
            return partiallyRenderedSelf;
        }
    }

    private String renderSelfWithSubTemplates(final Locale locale) throws IOException {
        renderSubTemplates(locale);
        return renderSelf(locale);
    }

    private void renderSubTemplates(Locale locale) {
        for (final Map.Entry<String, Template> subTemplateEntry : getSubTemplates().entrySet())
            args.put(subTemplateEntry.getKey(), subTemplateEntry.getValue().build(locale));
    }

    private String renderSelf(Locale locale) throws IOException {
        final ST st = createST(locale);

        for (final Map.Entry<String, Object> argsEntry : args.entrySet())
            st.add(argsEntry.getKey(), argsEntry.getValue());

        return st.render();
    }

    //

    private ST createST(final Locale locale) throws IOException {
        return new ST(getTemplateAsString(locale), '~', '~');
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
}


