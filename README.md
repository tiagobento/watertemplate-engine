Highlight Template Engine
===

This is the Main Template Engine used to build HTML pages.
It assumes that your template files are all stored in `classpath:templates/*` and that they are organized by locale.
This module works perfectly great with [Highlight i18n plugin](https://github.com/tiagobento/highlight/tree/master/web/i18n/).

If no Locale is provided, it will use `Locale.US`.

e.g.:

```bash
/src/main/resources/templates/en/index.html
/src/main/resources/templates/en_US/index.html
/src/main/resources/templates/pt/index.html
/src/main/resources/templates/pt_PT/index.html
```

All you need to know
===

Template.java
---
Make your template classes override `getTemplateFileURI`, `getSubTemplates` (optional) and `getMasterTemplate` (optional) to build your templates.

e.g.:

```java
public class MainPage extends Template {

    @Override
    protected Map<String, Template> getSubTemplates() {
        Map<String, Template> subTemplates = new HashMap<>();
        subTemplates.put("header", new HeaderPartial());
        subTemplates.put("footer", new FooterPartial());
        return subTemplates;
    }

    @Override
    public String getTemplateFileURI() {
        return "/main/main.html";
    }
}
```

On your `/main/main.html` file, you should have `~header~` and `~footer~` written somewhere.

e.g.:

```html
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>MainPage</title>
</head>
<body>
  ~header~
  ~content~
  ~footer~
</body>
</html>
```

**Note:** ~content~ is where the content of Templates that uses this Template as MasterTemplate goes.

If you want to put some data on your Template, you can use the `protected final Map<String, Object> args` to provide data during the build of your Template.

ResourceURI.java
---

Commonly, if your Template is an entire web page, it'll have an URI. `ResourceURI.java` have a method that helps you format your URI.

e.g.

```java
public class FooPage extends Template {

    @Override
    protected Map<String, Template> getSubTemplates() { ... }

    @Override
    protected Template getMasterTemplate() { ... }
    
    @Override
    public String getTemplateFileURI() { ... }
    
    public static class URI extends ResourceURI {
        public static final String RAW = "/foo/{id}";
        
        public static String format(Long id) {
          return format(RAW, id);
        }
    }
}
```

