Template Engine
===

Use to build HTML pages.
It assumes that your template files are all stored in `classpath:templates/*` and that they are organized by locale.

Default locale is `Locale.US`.

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
Make your template classes override `getTemplateFilePath`, `getSubTemplates` (optional) and `getMasterTemplate` (optional) to build your templates. Use `this.args` to input values to be used in your templates.

e.g.:

```java
public class MainPage extends Template {

    public MainPage() {
        this.args.put("title", "Home | Welcome");
    }

    @Override
    protected Map<String, Template> getSubTemplates() {
        Map<String, Template> subTemplates = new HashMap<>();
        subTemplates.put("header", new HeaderPartial());
        subTemplates.put("footer", new FooterPartial());
        return subTemplates;
    }

    @Override
    public String getTemplateFilePath() {
        return "/main/main.html";
    }
}
```

On your `/main/main.html` file, you should have `~title~`, `~header~` and `~footer~` written somewhere.

e.g.:

```html
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>~title~</title>
</head>
<body>
  ~header~
  ~content~
  ~footer~
</body>
</html>
```

The `String render(Locale)` method defined on `Template.java` is used to recursively build your template with its master/sub templates.

**Note:** ~content~ is where the content of Templates that uses this Template as MasterTemplate goes.

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
    public String getTemplateFilePath() { ... }
    
    public static class URI extends ResourceURI {
        public static final String RAW = "/foo/{id}";
        
        public static String format(Long id) {
          return format(RAW, id);
        }
    }
}
```

