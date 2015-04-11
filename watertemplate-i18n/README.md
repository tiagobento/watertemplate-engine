Water Template i18n maven plugin
===

Basic (and advanced) configuration:
---

`bundlesDir` is the path where your bundle files are stored.<br/>
`baseDir` is the path where your raw template files are stored.<br/>
`destinationDir` is the path to where your processed template files go.

**Note:** the default maven `phase` is *process-resources*, but you can naturally override it.


```xml
<plugin>
    <groupId>org.watertemplate</groupId>
    <artifactId>watertemplate-i18n-maven-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <configuration>
        <bundlesDir>${pom.basedir}/src/main/bundles</bundlesDir>
        <baseDir>${pom.basedir}/src/main/templates</baseDir>
        <destinationDir>${pom.basedir}/target/classes/templates</destinationDir>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

All the properties are **required**.

Usage
---
You can run this plugin with `mvn watertemplate-i18n:generate`

Templates
---
Typing `{{#i18n}}some.bundle.key{{/i18n}}` in your templates will make this plugin replace this whole string with a value contained in some of your bundle files.

Languages
---
This plugin uses the name of files stored in `bundlesDir` to determine which languages it does have to process.
Symbolic links are encouraged to avoid duplication.


e.g.


```bash
src/main/bundles/en.properties -> en_US.properties
src/main/bundles/en_US.properties
src/main/bundles/pt.properties -> pt_BR.properties
src/main/bundles/pt_BR.properties
```
