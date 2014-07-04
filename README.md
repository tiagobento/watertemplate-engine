Water Template Engine
===

Water Template Engine is an open-source modern Java template engine that simplifies the way you interact with templates.
With no external dependencies, it is very lightweight and robust.

[![Travis build on branch master](https://api.travis-ci.org/tiagobento/watertemplate-engine.svg?branch=master)](https://travis-ci.org/tiagobento/watertemplate-engine)



_NO_ configuration:
--
No complex annotations, no xml configuration, no thousands of modules dependency. Extending `Template`
gives you full power to build your templates. **Take a look at [examples](#java-api-and-template-syntax) and the source code!**

1 to 1 complexity:
---
Every template Java class has one, and one only, template file associated with it.
You cannot access other template files within your template class and you cannot access
other template Java classes within your template files.

Context isolation:
---
Because each template Java class has only one template file, it's reasonable that every
argument you add to your template through the `add` method lives only during the rendering
of the file you specified in `getFilePath` method of your template class.
That means that neither master nor sub templates can access arguments you've added in your template Java class.

_NO_ reflection:
--
Every reflection solution kills ALL tools of your IDE. Renaming, finding usages, moving etc.
Because your interface with your template files is only the `add` method, specified in `Template`, 
you can trust that **any refactor you make in your Java code will not propagate through your templates _silently_.**

Java API and template syntax:
--
[Template syntax] (https://github.com/tiagobento/watertemplate-engine/blob/master/src/test/resources/templates/en_US/beta/allCommands.html)
