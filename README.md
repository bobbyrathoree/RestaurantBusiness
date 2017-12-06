# Restaurant Business using Dropwizard.

## So what is Dropwizard?
As per [dropwizard.io] (http://www.dropwizard.io/1.0.5/docs/getting-started.html)
> Dropwizard straddles the line between being a library and a framework. Its goal is to provide performant, reliable implementations of everything a production-ready web application needs. Because this functionality is extracted into a reusable library, your application remains lean and focused, reducing both time-to-market and maintenance burdens.

To host our Restaurant Business application, we need a HTTP server to host it on.
Luckily, dropwizard provides us with [Jetty] (http://www.eclipse.org/jetty/), which is an incrediblely tuned HTTP server.

The two mainstream verbs provided by HTTP are GET and POST. But in order to build a RESTful application, we can't modify data using a GET request (or else it won't be RESTful). This is what PUT, POST and DELETE are for. Dropwizard includes for us [Jersey] (https://jersey.github.io/) which allows us to write clean, testable classes which gracefully map HTTP requests to simple Java objects.

Other libraries used by Dropwizard include Jackson (for JSON), Guava, Hibernate Validator, ([more..] (http://www.dropwizard.io/1.0.5/docs/getting-started.html))

## Using Maven to set up our project
First we need to add a *dropwizard.version* property in our POM.xml file. I have used 1.2.0, i.e one version older than the latest, to keep us from any unfixed, unfound bugs.
...
<properties>
	<dropwizard.version>1.2.0</dropwizard.version>
</properties>
...

Second, we'll add a bunch of *libraries as dependencies* that we'll be using in our project.
### For main purposes:
...
<dependencies>
        <dependency>
            <groupId>io.dropwizard</groupId>
            <artifactId>dropwizard-core</artifactId>
            <version>${dropwizard.version}</version>
        </dependency>

        <dependency>
            <groupId>io.dropwizard</groupId>
            <artifactId>dropwizard-db</artifactId>
            <version>${dropwizard.version}</version>
        </dependency>
</dependencies>
...

### For testing purposes:
...
	<dependency>
            <groupId>io.dropwizard</groupId>
            <artifactId>dropwizard-testing</artifactId>
            <version>${dropwizard.version}</version>
        </dependency>

        <dependency>
            <groupId>io.dropwizard</groupId>
            <artifactId>dropwizard-client</artifactId>
            <version>${dropwizard.version}</version>
        </dependency>

        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-all</artifactId>
            <version>1.9.5</version>
            <scope>test</scope>
        </dependency>
...

### For Database:
...
	<dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>6.0.6</version>
        </dependency>
	<dependency>
            <groupId>io.dropwizard</groupId>
            <artifactId>dropwizard-jdbi</artifactId>
            <version>${dropwizard.version}</version>
        </dependency>
...

### XML Part: DONE!

## Coding Part:

We **need** these types of classes while implementing Dropwizard in our project:
1. **Configuration class:** which specifies environment-specific parameters (including a .yml file, called YAML).
2. **Application class:** which forms the core of your Dropwizard application. It pulls together the various bundles and commands which provide basic functionality.
3. **Representation class:** which uses Jackson to perform JSON Serialization.
4. **Resource class:** which makes use of Jersey to give life to our code. Here we give URI/Path to our APIs, eg. @GET, @PUT, @POST each followed by @Path and provide implementations for the same.
5. **Health Check class:** which is a way of adding small tests to your application to allow you to verify that your application is functioning correctly in production.

### One more class:
1. DAO (Data Access Object) class: *What is DAO?* It is a object/interface, which is used to access data from database of data storage. *Why use it?* it abstracts the retrieval of data from a data resource such as a database. The concept is to **separate a data resource's client interface from its data access mechanism.**
In English, it means that the source of the data can change. Consider, for example, that your application is deployed in an environment that accesses an Oracle database. Then it is subsequently deployed to an environment that uses Microsoft SQL Server. In order to prevent rewriting your application to use SQL Server instead of Oracle, DAO creates a layer between your application logic and the data access.
