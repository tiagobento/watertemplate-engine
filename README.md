Water Template Engine
===

Water Template Engine is an open-source modern Java 8 template engine that simplifies the way you interact with templates.
With no external dependencies, it is very lightweight and robust.

Just like [mustache](https://github.com/janl/mustache.js), Water is a logic-less template engine, but it takes advantage of statically typed languages features to increase reliability and prevent errors.

[![Travis build on branch master](https://api.travis-ci.org/tiagobento/watertemplate-engine.svg?branch=master)](https://travis-ci.org/tiagobento/watertemplate-engine)

Table of contents
--

- [Quick start](#quick-start)
- [Configuration](#configuration)
- i18n
- [Nested templates](#nested-templates)
- [Adding arguments](#adding-arguments)
- [JAX-RS](#jax-rs)






## Quick start
##### Imagine a template:
```html
<h1>Months of ~year~</h1>
<ul>
    ~for month in months:
        <li>
            <span> ~month.lowerName~ </span>
            <span> with ~month.daysCount~ days </span>
        </li>
    :~
</ul>
``` 

##### Represent it in a Java class:
```java
class MonthsGrid extends Template {

    private static final Collection<Month> months = Arrays.asList(Month.values());

    MonthsGrid(final Year year) {
        add("year", year);
        addCollection("months", months, (month, map) -> {
            map.add("lowerName", month.name().toLowerCase());
            map.add("daysCount", month.length(year.isLeap()));
        });
    }

    @Override
    protected String getFilePath() {
        return "months_grid.html";
    }
}
```

##### Render it:
```java
public static void main(String[] args) {
    MonthsGrid monthsGrid = new MonthsGrid(2015);
    System.out.println(monthsGrid.render());
}
```

##### See the result:
```html
<h1>Months of 2015</h1>
<ul>
    <li>
        <span> january </span>
        <span> with 31 days </span>
    </li>
    <li>
        <span> february </span>
        <span> with 28 days </span>
    </li>
    <li>
        <span> march </span>
        <span> with 31 days </span>
    </li>
    <li>
        <span> april </span>
        <span> with 30 days </span>
    </li>
    
    ... and so on
    
</ul>
```




## Configuration
Add the [maven dependency]() to your project.
Since you've done that, extending `Template` gives you full power to build your templates. **Take a look at the [examples](watertemplate-example/src/main/java/org/watertemplate/example) and the source code!**

Read [this](#jax-rs) if you use RestEasy, Jersey or any JAX-RS implementation.




  
## Nested templates
Water gives you the possibility to nest templates in many levels. Each `Template` can have one `MasterTemplate` and many `SubTemplates`. When creating a `Template`, you can override the `getMasterTemplate` and `getSubTemplates` methods to specify how is your tree going to be.

Also, each `Template` has one, and one only, template file associated with it. This 1 to 1 relationship ensures that
you cannot access other template files within your `Template` and you cannot access other `Templates` within your template files.

See an [example](watertemplate-example/src/main/java/org/watertemplate/example/nestedtemplates).




## Adding arguments
Water works with a different approach to arguments. Unlike many other template engines, Water **uses no reflection at any** time and **doesn't make it possible to call functions within your template files**. Everything you add as an argument must have a key associated with it and can be formatted or manipulated through the mapping mechanism. There are three basic methods which let you add arguments:

```java
    add("email", user.getEmail()); 
    // Will match with ~email~
```

```java
    addMappedObject("user", user, (userMap) -> {
        userMap.add("email", user.getEmail());
    }); 
    // Will match with ~user.email~
```

```java
    addCollection("users", users, (user, userMap) -> {
        userMap.add("email", user.getEmail());
    });
    // Will match with ~for user in users: ~user.email~ :~
```
You can also nest `MappedObjects` or add them inside a collection mapping:

```java
    addCollection("users", users, (user, userMap) -> {
        userMap.addMappedObject("email", user.getEmail(), (email, emailMap) -> {
            emailMap("upper", email.toUpperCase());
        });
    });
    // Will match with
    //   ~for user in users: ~user.email~ :~
    // or also with
    //   ~for user in users: ~user.email.upper~ :~
```



## JAX-RS
If you want to provide your webpages as resources, JAX-RS is a good way to do that. Adding [this dependency]() to your project lets you return a `Template` object directly. The locale will be injected during the rendering of each call, so your i18n is safe.

```java
@GET
@Path("/home")
public Template getHomePage() {
    return new HomePage();
}
```
_NO_ function calls
--
Why enable function calls inside a template file if you can use the `addMappedObject` and the `addCollection` methods to **call functions you wrote in your Java files**? See an [explanatory example] (watertemplate-example/src/main/java/org/watertemplate/example/mappedobject/Main.java).
