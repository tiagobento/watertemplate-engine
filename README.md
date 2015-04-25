Water Template Engine
===

Water Template Engine is an open-source modern Java 8 template engine that simplifies the way you interact with templates.
With no external dependencies, it is very lightweight and robust.

Just like [mustache](https://mustache.github.io/), Water is a logic-less template engine, but it takes advantage of statically typed languages features to increase reliability and prevent errors.

[![Travis build on branch master](https://api.travis-ci.org/tiagobento/watertemplate-engine.svg?branch=master)](https://travis-ci.org/tiagobento/watertemplate-engine) [![Coverage Status](https://coveralls.io/repos/tiagobento/watertemplate-engine/badge.svg?branch=master)](https://coveralls.io/r/tiagobento/watertemplate-engine?branch=master)


- [Why to use Water?](#why-to-use-water)
- [Maven](#maven)
- [Quick start](#quick-start)
- [Documentation](#documentation)
 - [Nested templates](#nested-templates)
 - [Adding arguments](#adding-arguments)
 - [Commands](#commands)
 - [Conventions](#list-of-conventions)
- [i18n](#i18n)
- [JAX-RS](#jax-rs)
- **[Try it yourself!](#try-it-yourself)**



# Why to use Water?

Water is a not only logic-less, but also **transparent**. It means you see everything you do. It means there are no complex under-the-hood features which try to abstract the problems you are trying to solve.

Everything you do is explicit, and while the other template engines try to help you with reflection solutions or thousands of features which give you flexibility, Water restricts its use to its porpourse.

### _1 to 1_ complex
Every template class describes one and one only template file. Each of your .html or whatever you're templating are described by an specific class. It gives you a _coupling-free hierarchy_. Every template is independent. The relationships between templates are made inside your classes, not in your template files.

### _Transparent_
Transparency should be more often present in software artifacts. It is so easy to hide undesired things from its users that many people do it unconsciously. Water hides you nothing. Not even a simple `toString()` method is called without you calling it explicitly.

### _Logic-less_
Its obvious that no logic should be placed in your template files. But aren't include tags, nesting or parameterization logic? In Water templates, every these things are not possible inside template files. And even though Water provides the handy if command, it makes sure that every logic is still computed in your template classes by accepting only Booleans as conditions.

### _No_ function calls inside templates
Why enabling function calls inside your template files if you can do it in your Java classes? It may seem a feature less than the other engine templates. But it ensures your template files are actually not becoming programs.

### _No_ reflection
It's straight forward to say that reflection is either slow and dangerous. Even if it promisses that you're writing less code, it creates a complex environment which hides things from the developers. You end up not knowing exactly wheter functions are used or not.

### _No_ configuration
Water relies in very tiny amount of [conventions](#list-of-conventions) instead of providing non-obvious configuration. Adding the dependency to your project and extending `Template` give you full power to start building your templates.

### _No_ dynamic i18n
Water provides no dynamic i18n solution. There's no point in querying a .properties file millions of times during the lifecycle of your application. The [i18n project](#i18n) allows you to build your internationalized templates during build time. However, there are values which are _locale sensitive_, such as dates or currency. Water provides an elegant [solution](#adding-arguments) for such cases.



## Maven
Add the [maven dependency](http://mavenrepository.com/artifact/org.watertemplate/watertemplate-engine/1.1.0) to your project.

Read [this](#jax-rs) if you use RestEasy, Jersey or any JAX-RS implementation.



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
Save it to `classpath:templates/en_US/months_grid.html`. Read [this](#where-to-store-your-template-files) to know why to save in this specific path.

##### Represent it in a Java class:
```java
class MonthsGrid extends Template {

    private static final Collection<Month> months = Arrays.asList(Month.values());

    MonthsGrid(final Year year) {
        add("year", year.toString());
        addCollection("months", months, (month, map) -> {
            map.add("lowerName", month.name().toLowerCase());
            map.add("daysCount", month.length(year.isLeap()) + "");
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
    MonthsGrid monthsGrid = new MonthsGrid(Year.of(2015));
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



#Documentation

 
### Adding arguments
Water works with a different approach to arguments. Unlike many other template engines, Water **uses no reflection at any time** and **doesn't make it possible to call functions within your template files**. Everything you add as an argument must have a key associated with it and can be formatted or manipulated through the mapping mechanism. 
There are five basic methods which let you add arguments:

```java
add("email", user.getEmail()); // takes a String
// Will match with ~email~
```

```java
add("user_is_popular", user.isPopular()); // takes a Boolean
// Will match with ~user_is_popular~
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

```java
addLocaleSensitiveObject("now", new Date(), (now, locale) -> {
    return DateFormat.getDateInstance(DateFormat.FULL, locale).format(now); // returns a String
});
// Will match with ~now~
```


You can also nest `MappedObjects` and `LocaleSensitiveObjects` or add them inside a collection mapping:

```java
addCollection("users", users, (user, userMap) -> {
    userMap.addMappedObject("name", user.getName(), (name, nameMap) -> {
        nameMap.add("upper", name.toUpperCase());
    });
    userMap.addLocaleSensitiveObject("birth_date", user.getBirthDate(), (birthDate, locale) -> {
        return DateFormat.getDateInstance(DateFormat.FULL, locale).format(birthDate);
    });
});
// Will match with
//   ~for user in users: ~user.name~ was born in ~user.birth_date~ :~
// or also with
//   ~for user in users: ~user.name.upper~ was born in ~user.birth_date~ :~
```

It is only possible to add Strings and Booleans. Collections and MappedObjects are special types which should never be evaluated. **The `toString()` method is never implicitly called.**

### Nested templates
Water gives you the possibility to nest templates in many levels. Each `Template` can have one `MasterTemplate` and many `SubTemplates`. When creating a `Template`, you can override the `getMasterTemplate` and `getSubTemplates` methods to specify how is your tree going to be.

Also, each `Template` has one, and one only, template file associated with it. This 1 to 1 relationship ensures that
you cannot access other template files within your `Template` and you cannot access other `Templates` within your template files.

See an [example](watertemplate-example/src/main/java/org/watertemplate/example/nestedtemplates).





### Commands
Water provides **if** and **for** commands. 



- **_If:_** The if condition _must_ be a boolean. Null objects are not a valid condition.

- **_For:_** The for collection _must_ be added by the `addCollection` method. The else is triggered when the collection is empty or null.

#### Full syntax
```html
~for user in users:
    
    <span> ~user.name~ </span>

    ~if user.is_already_followed:
        <input type="button" value="Unfollow"/>
    :else:
        <input type="button" value="Follow"/>
    :~
    
:else:
    <span> No users to display </span>
:~
```


### List of conventions

- `~content~` is a reserved identifier. It's where your Template goes inside its Master template.

- Every template file must be placed in `classpath:templates/[locale]/`. The [i18n project ](#i18n) helps you with that.

- The default locale is `Locale.US`. However, you can change it easily. [See how](#how-to-change-the-default-locale).

- When using the [dev-mode]() flag, your templates must be placed in `src/main/resources/templates`

- _[Temporary]_ The characters `:` and `~` cannot be missplaced in your template files. It will lead to unexpected behavior during the compile fase.



### How to change the default locale?
Every `Template` has a method called `getDefaultLocale` which you can override. If you want to change the default locale for every template it's recommended that you create a class in the middle of `Template` and your `Templates` which overrides this method and propagates the change to its child classes.

## i18n
Water provides an i18n solution too. See the [i18n project](watertemplate-i18n) to know how to use it and why it works so good together with the engine.

## JAX-RS
If you want to provide your webpages as resources, JAX-RS is a good way to do that. Adding [this dependency](http://mavenrepository.com/artifact/org.watertemplate/watertemplate-jaxrs-binding/1.1.0) to your project lets you return a `Template` object directly. The locale will be injected during the rendering of each call, so your i18n is safe.

**Run an example** following the information below.

```java
@GET
@Path("/home")
public Template homePage() {
    return new HomePage();
}

@GET
@Path("/months/{year}")
public Template monthsGrid(@PathParam("year") Integer year) {
    return new MonthsGrid(Year.of(year));
}
```

## Try it yourself!

Go to the [examples project](watertemplate-example/) and follow the instructions.
