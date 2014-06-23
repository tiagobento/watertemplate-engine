package com.highlight.template;

class TemplateException extends RuntimeException {
    public TemplateException(Exception e) {
        super(e);
    }

    public TemplateException(String s) {
        super(s);
    }
}
