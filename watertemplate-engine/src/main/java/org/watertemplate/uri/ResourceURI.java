package org.watertemplate.uri;

import org.watertemplate.exception.TemplateException;
import org.watertemplate.uri.exception.NotEnoughArgumentsException;

public abstract class ResourceURI {

    private static final String PATH_PARAM_REGEX = "\\{\\w+\\}";

    public static String format(String staticPath, final Object... args) {
        for (final Object arg : args)
            staticPath = staticPath.replaceFirst(PATH_PARAM_REGEX, arg.toString());

        if (staticPath.matches(".*" + PATH_PARAM_REGEX + ".*"))
            throw new NotEnoughArgumentsException(staticPath);

        return staticPath;
    }
}
