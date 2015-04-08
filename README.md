Water Template Engine
===

Water Template Engine is an open-source modern Java template engine that simplifies the way you interact with templates.
With no external dependencies, it is very lightweight and robust.

Just like [mustache](https://github.com/janl/mustache.js), Water is a logic-less template engine, but it takes advantage of statically typed languages features to increase reliability and prevent errors.

[![Travis build on branch master](https://api.travis-ci.org/tiagobento/watertemplate-engine.svg?branch=master)](https://travis-ci.org/tiagobento/watertemplate-engine)

#### Imagine a template:
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

#### Represent it in a Java class:
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

#### Render it:
```java
public static void main(String[] args) {
    MonthsGrid monthsGrid = new MonthsGrid(2015);
    System.out.println(monthsGrid.render());
}
```

#### See the result:
```html
<h1>Months of 2014</h1>
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
    
_NO_ configuration
--
No complex annotations, no xml configuration, no thousands of modules dependency. Extending `Template`
gives you full power to build your templates. **Take a look at the [examples](watertemplate-example/src/main/java/org/watertemplate/example/collection/Main.java) and the source code!**

_NO_ reflection
--
Every reflection solution kills most of refactoring tools on IDEs. Renaming, finding usages, moving etc.
Because your interface with your template files is only the `add` methods, specified in `Template`, 
you can trust that **any refactor you make in your Java code will not propagate through your templates _silently_.**

_NO_ function calls
--
Why enable function calls inside a template file if you can use the `addMappedObject` and the `addCollection` methods to **call functions you wrote in your Java files**? See an [explanatory example] (watertemplate-example/src/main/java/org/watertemplate/example/mappedobject/Main.java).

1 to 1 complexity
---
Every template Java class has one, and one only, template file associated with it.
You cannot access other template files within your template class and you cannot access
other template Java classes within your template files.

Context isolation
---
Because each template Java class has only one template file, it's reasonable that every
argument you add to your template through the `add` method lives only during the rendering
of the file you specified in `getFilePath` method of your template class.
That means that neither master nor sub templates can access arguments you've added in your template Java class.
