package org.watertemplate.template.uri;

import org.watertemplate.template.TemplateException;

public abstract class ResourceURI {

    private static final String PATH_PARAM_REGEX = "\\{\\w+\\}";

    public static String format(String staticPath, final Object... args) {
        for (final Object arg : args)
            staticPath = staticPath.replaceFirst(PATH_PARAM_REGEX, arg.toString());

        if (staticPath.matches(".*" + PATH_PARAM_REGEX + ".*"))
            throw new TemplateException("Not enough arguments.");

        return staticPath;
    }
}
