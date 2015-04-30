Water Template i18n maven plugin
===
This maven plugin helps you organizing and generation your internationalized template files. It allows you to choose where to store your _raw_ template files and where to put the internationalized versions of the same templates. **It all happens in build time**, so you waste 0 time processing i18n during the execution of your application.

Table of contents
--
- [Configuration](#configuration)
- [Usage](#usage)

## Configuration

`bundlesDir` is the path where your bundle files are stored.<br/>
`baseDir` is the path where your raw template files are stored.<br/>
`destinationDir` is the path to where your processed template files go.

```xml
<plugin>
    <groupId>org.watertemplate</groupId>
    <artifactId>watertemplate-i18n-maven-plugin</artifactId>
    <version>1.1.0</version>
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

**Note:** the default maven `phase` is *process-resources*, but you can naturally override it.

## Usage

```html
<!--`baseDir`/hi.html -->
<span> {{i18n}}hi{{/i18n}}! :) </span>
```

```properties
## `bundlesDir`/en_US.properties
hi = Hi
```

```properties
## `bundlesDir`/pt_BR.properties
hi = Oi
```
Run `mvn watertemplate-i18n:generate` and you'll have

```html
<!--`destinationDir`/en_US/hi.html -->
<span> Hi! :) </span>
```

```html
<!--`destinationDir`/pt_BR/hi.html -->
<span> Oi! :) </span>
```

The name of files stored in `bundlesDir` are used to determine which languages it has to process.
Symbolic links are encouraged to avoid duplication.

e.g.

```bash
`bundlesDir`/en.properties -> en_US.properties
`bundlesDir`/en_US.properties
`bundlesDir`/pt.properties -> pt_BR.properties
`bundlesDir`/pt_BR.properties
```
