# Restaurant Business using Dropwizard.

## What is Dropwizard?
As per [dropwizard.io] (http://www.dropwizard.io/1.0.5/docs/getting-started.html)
> Dropwizard straddles the line between being a library and a framework. Its goal is to provide performant, reliable implementations of everything a production-ready web application needs. Because this functionality is extracted into a reusable library, your application remains lean and focused, reducing both time-to-market and maintenance burdens.

To host our Restaurant Business application, we need a HTTP server to host it on.
Luckily, dropwizard provides us with [Jetty] (http://www.eclipse.org/jetty/), which is an incrediblely tuned HTTP server.

The two mainstream verbs provided by HTTP are GET and POST. But in order to build a RESTful application, we can't modify data using a GET request (or else it won't be RESTful). This is what PUT, POST and DELETE are for. Dropwizard includes for us [Jersey] (https://jersey.github.io/) which allows us to write clean, testable classes which gracefully map HTTP requests to simple Java objects.

Other libraries used by Dropwizard include Jackson (for JSON), Guava, Hibernate Validator, ([more..] (http://www.dropwizard.io/1.0.5/docs/getting-started.html))

## Using Maven to set up our project
First we need to add a *dropwizard.version* property in our POM.xml file. I have used 1.2.0, i.e one version older than the latest, to keep us from any unfixed, unfound bugs.

Second, we'll add a bunch of *libraries as dependencies* that we'll be using in our project.
###  Main dependencies:
```
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
```

### Test dependencies:
```
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
```       

###  Database dependencies:
```
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
```

## SQL/DB setup
Find restaurants.sql schema file under root folder and execute via mySQL command line. Also update restaurant.yml file with appropriate SQL DB username and password

**restaurant.sql**
```concept
create database internship_dataone;
use internship_dataone;
create table orders(
  -- primary key
  id bigint primary key not null auto_increment,
  customer_name varchar(255) not null,
  item_ordered varchar(255) not null
);

INSERT INTO orders (customer_name, item_ordered) values ('Bobby', 'Sandwich');

INSERT INTO orders (customer_name, item_ordered) values ('Rohit', 'Burger');

INSERT INTO orders (customer_name, item_ordered) values ('Ashish', 'Fries');

INSERT INTO orders (customer_name, item_ordered) values ('Nikunj', 'Cola');
```

**restaurant.yml:**
```concept
database:
  driverClass: com.mysql.cj.jdbc.Driver
  url: jdbc:mysql://localhost/internship_dataone
  user: YOUR_DB_USERNAME
  password: YOUR_DB_PASSWORD
  ...
```

## Compile and run unit tests
Once repository is cloned/downloaded in a folder, use below maven command to build a fatJar (which will also run unit tests)
```concept
$mvn package
```

Once above maven command is executed, a jar called **RestaurantBusiness-1.0-SNAPSHOT.jar** can be found under "target" folder. To start RestaurantBusiness dropwizard server, execute:

```concept
$java -jar target/RestaurantBusiness-1.0-SNAPSHOT.jar server restaurant.yml
```

## cURL commands
```concept
View All Orders:
curl -v "http://localhost:8080/getAllOrders"
```
```
View a specific order:
curl -v "http://localhost:8080/getOrder/1" -X GET
```
```
Place a new order:
curl -v "http://localhost:8080/putOrder" -X POST -d '{"customer_name":"Ruby","item_ordered":"Sandwitch2"}' -H "Content-Type: application/json" 
```
```
Edit an existing order:
curl -v "http://localhost:8080/editOrder/6" -X PUT -d '{"customer_name":"Ruby","item_ordered":"Sandwitch and Cola"}' -H "Content-Type: application/json"
```


## Documentation:

We **need** these types of classes while implementing Dropwizard in our project:
1. **Configuration class:** which specifies environment-specific parameters (including a .yml file, called YAML).
2. **Application class:** which forms the core of your Dropwizard application. It pulls together the various bundles and commands which provide basic functionality.
3. **Representation class:** which uses Jackson to perform JSON Serialization.
4. **Resource class:** which makes use of Jersey to give life to our code. Here we give URI/Path to our APIs, eg. @GET, @PUT, @POST each followed by @Path and provide implementations for the same.
5. **Health Check class:** which is a way of adding small tests to your application to allow you to verify that your application is functioning correctly in production.
