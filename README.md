Water Template Engine
===

Water Template Engine is an open-source modern Java template engine that simplifies the way you interact with templates.
With no external dependencies, it is very lightweight and robust.

_NO_ configuration:
--
No complex annotations, no xml configuration, no thousands of modules dependency. Extending `Template`
gives you full power to build your templates. **Take a look at the source code!**

1 to 1 complexity:
---
Every template Java class has one, and one only, template file associated with it.
You cannot access other template files within your template class and you cannot access
other template Java classes within your template files.

Context isolation:
---
Because each template Java class has only one template file, it's reasonable that every
argument you add to your template through the `add` method lives only during the rendering
of the file you specified in `getTemplateFilePath` method of your template class.
That means that neither master nor sub templates can access arguments you've added in your template Java class.

_NO_ reflection:
--
Every reflection solution kills ALL tools of your IDE. Renaming, finding usages, moving etc.
Because your interface with your template files is only the `add` method, specified in `Template`, 
you can trust that **any refactor you make in your Java code will not propagate through your templates _silently_.**

Easy to read and learn:
---
```java
public class LayoutTemplate extends Template {

    public LayoutTemplate() {
        add("title", "Profile | Water Template Engine");
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("header", new HeaderPartial());
        subTemplates.add("footer", new FooterPartial());
    }

    @Override
    public String getTemplateFilePath() {
        return "/layout/main.html";
    }
}

public class ProfilePage extends Template {

    private User user;

    public ProfilePage(User user) {
        this.user = user;
    }
    
    @Override
    protected Template getMasterTemplate() {
        return new LayoutTemplate();
    }

    @Override
    protected void addSubTemplates(TemplateMap.SubTemplates subTemplates) {
        subTemplates.add("userInfo", new UserInfoPartial(user));
        subTemplates.add("posts", new PostsPartial(user.getPosts()));
    }

    @Override
    public String getTemplateFilePath() {
        return "/profile/profile.html";
    }
}
```
