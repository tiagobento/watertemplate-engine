package com.highlight.template;

import com.highlight.template.renderer.STRenderer;
import org.apache.commons.io.FileUtils;

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
    private final Map<String, Object> args = new HashMap<>();

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

    final String render() {
        return render(DEFAULT_LOCALE);
    }

    final String render(final Locale locale) {
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

    protected void add(final String key, final Object value) {
        this.args.put(key, value);
    }

    private String fullyRenderSelfAndCacheIfNecessary(final Locale locale) throws IOException {
        final String renderedSelfWithRenderedSubTemplates = renderSelfWithSubTemplates(locale);
        final String fullyRenderedSelf = renderMasterTemplateIfNecessary(renderedSelfWithRenderedSubTemplates, locale);

        StaticTemplatesCache.cacheIfNecessary(getClass(), locale, fullyRenderedSelf);

        return fullyRenderedSelf;
    }

    private String renderMasterTemplateIfNecessary(final String partiallyRenderedSelf, final Locale locale) {
        final Template masterTemplate = getMasterTemplate();
        if (masterTemplate != null) {
            masterTemplate.add("content", partiallyRenderedSelf);
            return masterTemplate.render(locale);
        } else {
            return partiallyRenderedSelf;
        }
    }

    private String renderSelfWithSubTemplates(final Locale locale) throws IOException {
        renderSubTemplates(locale);
        return renderSelf(locale);
    }

    private void renderSubTemplates(final Locale locale) {
        for (final Map.Entry<String, Template> subTemplateEntry : getSubTemplates().entrySet())
            add(subTemplateEntry.getKey(), subTemplateEntry.getValue().render(locale));
    }

    private String renderSelf(final Locale locale) throws IOException {
        final String templateAsString = getTemplateAsString(locale);
        return new STRenderer(templateAsString, args).render();
    }

    //

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


