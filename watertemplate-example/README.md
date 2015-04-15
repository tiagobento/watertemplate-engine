## JAX-RS Binding

**1.** Clone and build the project

```bash
git clone https://github.com/tiagobento/watertemplate-engine
cd watertemplate-engine
mvn clean install
```

**2.** Go to the examples directory and run the Jetty plugin

```bash
cd watertemplate-example
mvn jetty:run
```

**3.** Open your favorite browser
 - Go to [localhost:8080/home](http://localhost:8080/home) to see [this example](src/main/java/org/watertemplate/example/collection)
 - Or to [localhost:8080/months/2015](http://localhost:8080/months/2015) to see [this one](src/main/java/org/watertemplate/example/nestedtemplates)

## i18n

**1.** Clone the project
```bash
git clone https://github.com/tiagobento/watertemplate-engine
```

**2.** Look the raw template files
```bash
find watertemplate-engine/watertemplate-example/src/main/i18n-templates
```

**3.** Build the project and see the parsed template files
```bash
mvn clean install
find watertemplate-engine/watertemplate-example/src/main/i18n-generated-templates
```

You can see the configuration [here](pom.xml) at the end of the file.
