package com.highlight.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class TemplateMessageBodyWriter implements MessageBodyWriter<Template> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateMessageBodyWriter.class);

    @Context
    private HttpServletRequest request;

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return Template.class.isAssignableFrom(aClass) && MediaType.TEXT_HTML_TYPE.equals(mediaType);
    }

    @Override
    public long getSize(Template template, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Template template, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> stringObjectMultivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        long start = System.currentTimeMillis();
        String build = template.build(request.getLocale());
        LOGGER.info("Rendered {} in {}ms", template.getClass().getSimpleName(), System.currentTimeMillis() - start);
        outputStream.write(build.getBytes());
        outputStream.flush();
    }

}
