# Astra Demo - Cassandra DBaaS

This project is a sample for demanstrate the use of Datastax ASTRA the Cassandra DBaaS

It has been inspired by the work of Adron Hall :

https://dev.to/betterbotz/creating-an-express-server-for-better-botz-4b00</br>
https://www.datastax.com/blog/2020/03/introducing-better-botz</br>
https://github.com/DataStax-Academy/better-botz-workshop-online</br>

In this projects, we will going to use Java and Spring.

The prerequisites :

Java 1.8 : https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html</br>
Any IDE, I've used Spring Tools Suite : https://spring.io/tools#suite-three</br>


## Create the Database

To register use this link :

[DataStax Astra Database-as-a-Service](https://astra.datastax.com/register)



![](https://raw.githubusercontent.com/bguedes/static/master/astraRegister.png)



![](https://raw.githubusercontent.com/bguedes/static/master/astraCreateDatabaseStep1.png)

Let parameters by default :

**Compute Size**</br>
    Free tier</br>
**Estimated Cost**</br>
    Per Hour</br>

Specify these parameters :

**Database name**           astrademo</br>
**Keyspace name**	    astrademo</br>
**Database username**       datastax</br>
**Database user password**  datastax</br>
</br>
**Database location**  europe-west1</br>

The database creation could take some minutes to acheive :

![](https://raw.githubusercontent.com/bguedes/static/master/astraCreateDatabaseProcessing.png)

Please wait !!

After creation, you will able to use it :

![](https://raw.githubusercontent.com/bguedes/static/master/astraDatabaseCreated.png)

Please download the bundle file needed for Authentification, you will need this zip file for be able to connect the database through your application.

![](https://raw.githubusercontent.com/bguedes/static/master/astraDatabaseBundleAuthenticationFile.png)

The file is called **secure-connect-astrademo.zip**

For the database that's all Folks

## Get a sample project

You can use a sample project cloning this github repository :

```console
https://github.com/bguedes/astra.demo.git
```

You just need to copy your zip file **secure-connect-astrademo.zip** downloaded before and copy it on the <projec folder>

![](https://raw.githubusercontent.com/bguedes/static/master/astraBundleFile.png)

Just launch Spring :

```console
./mvnw spring-boot:run
```

Open a web client :

http://localhost:8080/api/v1/products

You'll get the product list screen :

![](https://raw.githubusercontent.com/bguedes/static/master/astraDemoProductsList.png)

Just play creating products and remove them !!! Enjoy ....

## Create a sample project

[Spring Initializer](https://start.spring.io/)

Let parameters by default :

**Project :*
	Maven Project

**Language :**
	Java

**Spring Boot :**
	2.3.0

**Packaging**
	Jar

**Java**
	8

Specify these parameters :

**Group**
	com.datastax.astra

**Artifact**
	demo

**Name**
	demo

**Description**
	Demo project for Datastax Astra using Spring Boot

**Package name**
	com.datastax.astra.demo


![](https://raw.githubusercontent.com/bguedes/static/master/springInitializrProject.png)


**Add the following dependencies :**
	Spring Web
	Apache Freemarker
	Lombok
	Spring Data for Apache Cassandra


![](https://raw.githubusercontent.com/bguedes/static/master/springInitializrDependencies1.png)

![](https://raw.githubusercontent.com/bguedes/static/master/springInitializrDependenciesFreemarker.png)

![](https://raw.githubusercontent.com/bguedes/static/master/springInitializrDependenciesLombok.png)

![](https://raw.githubusercontent.com/bguedes/static/master/springInitializrDependenciesCassandra.png)

Generate the project and download the project jar

![](https://raw.githubusercontent.com/bguedes/static/master/SpringInitialiserFinished.png)


![](https://raw.githubusercontent.com/bguedes/static/master/projectPackageExplorerSimple.png)


**application.conf**

```json
datastax-java-driver {
  basic {
    session-keyspace = astrademo
    cloud {
      secure-connect-bundle = secure-connect-astrademo.zip
    }
  }
  advanced {
    auth-provider {
      class = PlainTextAuthProvider
      username = datastax
      password = datastax
    }
  }
}
```

**CassandraConfiguration.java**

```java
package com.datastax.astra.astra.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

@Configuration
@EnableCassandraRepositories(basePackages = "com.example.astra.betterbotapp")
@PropertySource(value = { "classpath:application.conf" })
public class CassandraConfiguration {

    @Value("${cassandra.keyspaceName}")
    public String keyspaceName;

    @Bean
    public CqlIdentifier keyspace() {
      return CqlIdentifier.fromCql(keyspaceName);
    }

    @Bean
    public CqlSession cqlSession() {
        return CqlSession.builder().build();
    }

    public String getKeyspaceName() {
        return keyspaceName;
    }

    public void setKeyspaceName(String keyspaceName) {
        this.keyspaceName = keyspaceName;
    }
}
```

**application.yml**

```yaml
cassandra:
  keyspaceName: astrademo

spring:
  data:
    cassandra:
      schemaAction: CREATE_IF_NOT_EXISTS
```
**Order.java**

```java
package com.datastax.astra.astra.demo;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table(value = "orders")
@Data
public class Order {

    @Column("customer_name")
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String customerName;

    private UUID id;

    private String address;

    @Column("prod_id")
    private UUID prouctdId;

    @Column("prod_name")
    private String productName;


    private String description;

    private float price;

    @Column("sell_price")
    private float sellPrice;

}
```

**Product.java**

```java
package com.datastax.astra.astra.demo;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Table(value = "products")
@Data
public class Product {

	private UUID id;

	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	private String name;

	private String description;

	@CassandraType(type = Name.DECIMAL)
	private Float price;

	@CassandraType(type = Name.TIMESTAMP)
	private Date created;

}
```


**IProductRepository.java**

```java
package com.datastax.astra.astra.demo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.datastax.astra.astra.demo.model.Product;

@Repository
public interface IProductRepository extends CassandraRepository<Product, String> {

	void deleteByName(String name);

	Product findByName(String name);
}

``` 	

**IOrder.java**

```java
package com.datastax.astra.astra.demo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.datastax.astra.astra.demo.model.Order;

@Repository
public interface IOrder extends CassandraRepository<Order, String> {

}

```

**ProductService.java**

```java
package com.datastax.astra.astra.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.astra.astra.demo.dao.IProductRepository;
import com.datastax.astra.astra.demo.model.Product;

@Service
public class ProductService {

	@Autowired
	private IProductRepository productRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public void add(Product product) {
		productRepository.save(product);
	}

	public void remove(String productName) {		
		productRepository.deleteByName(productName);
	}

	public Product findByName(String productName) {
		return productRepository.findByName(productName);
	}
}
```

**ProductController.java**

```java
package com.datastax.astra.astra.demo;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datastax.astra.astra.demo.model.Product;
import com.datastax.astra.astra.demo.service.ProductService;

@Controller
@RequestMapping("/api/v1/")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public String products(@ModelAttribute("model") ModelMap model) {
	    model.addAttribute("products", productService.findAll());
	    return "products";
	}

	@GetMapping("/product/{productName}")
	public ResponseEntity<Product> findProductsByName(@PathVariable String productName) {
	    Product productRetreived = productService.findByName(productName);
            return new ResponseEntity<Product>(productRetreived, HttpStatus.OK);
	}


	@PostMapping("/product")
	public String add(Product product) {
	    product.setId(UUID.randomUUID());
	    product.setCreated(new Date(System.currentTimeMillis()));
	    productService.add(product);
	    return "redirect:/api/v1/products";
	}

	@DeleteMapping("/product/{productName}")
	public ResponseEntity<String> delete(@PathVariable String productName) {
	    productService.remove(productName);
            return new ResponseEntity<String>(productName, HttpStatus.OK);		
	}
}
```


**Application.java**

```java
package com.datastax.astra.astra.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import com.datastax.oss.driver.api.core.CqlSession;

@SpringBootApplication
public class Application implements CommandLineRunner 	{

    @Autowired
    private CqlSession astraSession;

    @Value("classpath:cql/products.cql")
    private Resource cqlImport;

	public static void main(String[] args) {
	    SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	    loadDataUsingCQL();
	}

	private void loadDataUsingCQL() {

	    List<String> cqlScripts = new ArrayList<>();

	    try (InputStream inputStream = cqlImport.getInputStream()) {

	        ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
		}

		cqlScripts = Arrays.asList(result.toString("UTF-8").split(";"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    for (String cqlScript : cqlScripts) {
	        astraSession.execute(cqlScript);
	    }
	}

}
```

**products.cql**

```sql
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb0,'Heavy Lift Arms','Heavy lift arms capable of lifting 1,250 lbs of weight per arm. Sold as a set.',4199.99,'2019-01-10 09:48:31.020+0040') IF NOT EXISTS;
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb1,'Precision Action Arms','Arms for precision activities in manufacturing or repair. Sold as a set.',12199.99,'2019-01-10 09:28:31.020+0040') IF NOT EXISTS;
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb2,'Medium Lift Arms','Medium lift arms capable of lifting 850 lbs of weight per arm. Sold as a set.',3199.99,'2019-01-10 09:23:31.020+0040') IF NOT EXISTS;
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb3,'Drill Arms','Arms for drilling into surface material. Sold as a set. Does not include drill bits.',2199.99,'2019-01-10 09:12:31.020+0040') IF NOT EXISTS;
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb4,'High Process AI CPU','Head processor unit for robot with heavy AI job process capabilities.',2199.99,'2019-01-10 18:48:31.020+0040') IF NOT EXISTS;
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb5,'Basic Task CPU','Head processor unit for robot with basic process tasks.',899.99,'2019-01-10 18:48:31.020+0040') IF NOT EXISTS;
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb6,'High Strength Torso','Robot body with reinforced plate to handle heavy workload and weight during jobs.',2199.99,'2019-01-10 18:48:31.020+0040') IF NOT EXISTS;
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb7,'Medium Strength Torso','Robot body to handle general jobs.',1999.99,'2019-01-10 18:48:31.020+0040') IF NOT EXISTS;
INSERT INTO products (  id ,  name ,  description,  price,  created ) VALUES (31047029-2175-43ce-9fdd-b3d568b19bb8,'Precision Torso','Robot torso built to handle precision jobs with extra stability and accuracy reinforcement.',8199.99,'2019-01-10 18:48:31.020+0040') IF NOT EXISTS;
```

**products.ftlh**

```html
<!DOCTYPE html>
<html lang="en">
<head>
<title>ASTRA Demo - Product list</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script
  src="https://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous">
</script>
<script>

$(document).ready(function(){
    $('.bnt-delete-product').click(function() {

   	var productName = $(this).val();

        $.ajax({
            type: "DELETE",
            url: '/api/v1/product/' + productName,
            success: function (data) {
                top.location.href = '/api/v1/products';
            },
            error: function (data) {
                console.log('Error:', data);
            }   		
    	});
   	});
});

</script>
<style>
.container {
	margin-top: 80px;
}
.bg-dark {
	background-color: #3b8dbd !important;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-xl navbar-dark bg-primary fixed-top">
		<a class="navbar-brand mb-0 h4" href="https://www.datastax.com/">ASTRA Demo - Product list</a>

	</nav>
	<div class="container">

		<form class="form-inline" method="post" action="/api/v1/product">

			<input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0"
				id="name" name="name" placeholder="Name" />

			<input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0"
				id="description" name="description" placeholder="Description" />

			<input type="float" class="form-control mb-2 mr-sm-2 mb-sm-0"
				id="price" name="price" placeholder="price" />										

		  <button type="submit" class="btn btn-primary">Add</button>
		</form>

		<table class="table">
			  <thead>
			    <tr>
			      <th>Id</th>
			      <th>Product name</th>
			      <th>Description</th>
			      <th>Price</th>
			      <th>Creation date</th>	      
			      <th></th>
			    </tr>
			  </thead>
			  <tbody>
			    <#list model["products"] as product>
				    <tr>
				      <th scope="row">${product.id}</th>
				      <td>${product.name}</td>
				      <td>${product.description}</td>
				      <td>${product.price}</td>
				      <td>${product.created?string("yyyy.MM.dd")}</td>	      
				      <td>
                        <button class="btn btn-sm btn-danger bnt-delete-product"
                        value="${product.name}">Delete</button>
				      </td>
				    </tr>
			    </#list>
			  </tbody>
		</table>

	</div>
</body>
</html>
```

```console
./mvnw spring-boot:run
```

```console
.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::        (v2.3.0.RELEASE)

2020-05-25 03:00:05.950  INFO 30491 --- [           main] c.datastax.astra.astra.demo.Application  : Starting Application on bruno-Precision-5510 with PID 30491 (/media/bruno/BGuedes/Datastax/AstraWorkShop/SpringBootProject/astra.demo/target/classes started by bruno in /media/bruno/BGuedes/Datastax/AstraWorkShop/SpringBootProject/astra.demo)
2020-05-25 03:00:05.952  INFO 30491 --- [           main] c.datastax.astra.astra.demo.Application  : No active profile set, falling back to default profiles: default
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.codehaus.groovy.vmplugin.v7.Java7$1 (file:/home/bruno/.m2/repository/org/codehaus/groovy/groovy/2.5.7/groovy-2.5.7-indy.jar) to constructor java.lang.invoke.MethodHandles$Lookup(java.lang.Class,int)
WARNING: Please consider reporting this to the maintainers of org.codehaus.groovy.vmplugin.v7.Java7$1
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
2020-05-25 03:00:06.511  INFO 30491 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Cassandra repositories in DEFAULT mode.
2020-05-25 03:00:06.518  INFO 30491 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 4ms. Found 0 Cassandra repository interfaces.
2020-05-25 03:00:06.647  INFO 30491 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Reactive Cassandra repositories in DEFAULT mode.
2020-05-25 03:00:06.672  INFO 30491 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 24ms. Found 0 Reactive Cassandra repository interfaces.
2020-05-25 03:00:06.675  INFO 30491 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Cassandra repositories in DEFAULT mode.
2020-05-25 03:00:06.694  INFO 30491 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 18ms. Found 2 Cassandra repository interfaces.
2020-05-25 03:00:07.022  INFO 30491 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2020-05-25 03:00:07.031  INFO 30491 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-05-25 03:00:07.031  INFO 30491 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.35]
2020-05-25 03:00:07.097  INFO 30491 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-05-25 03:00:07.097  INFO 30491 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 993 ms
2020-05-25 03:00:07.630  INFO 30491 --- [           main] c.d.o.d.i.core.DefaultMavenCoordinates   : DataStax Java driver for Apache Cassandra(R) (com.datastax.oss:java-driver-core) version 4.6.1
2020-05-25 03:00:07.991  INFO 30491 --- [     s0-admin-0] c.d.oss.driver.internal.core.time.Clock  : Using native clock for microsecond precision
2020-05-25 03:00:08.210  INFO 30491 --- [        s0-io-0] c.d.oss.driver.api.core.uuid.Uuids       : PID obtained through native call to getpid(): 30491
2020-05-25 03:00:09.353  INFO 30491 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-05-25 03:00:09.575  INFO 30491 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2020-05-25 03:00:09.583  INFO 30491 --- [           main] c.datastax.astra.astra.demo.Application  : Started Application in 3.925 seconds (JVM running for 4.193)
```

http://localhost:8080/api/v1/products

![](https://raw.githubusercontent.com/bguedes/static/master/astraDemoProductsList.png)

## Rest client usage

For the demo purpose it has been used this REST client :</br>
https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo

It's up to you to use another Rest client or curl ...

### Get all products list

![](https://raw.githubusercontent.com/bguedes/static/master/restGetAllProducts.png)

![](https://raw.githubusercontent.com/bguedes/static/master/restGetAllProductsResult.png)

### Adding a new product

![](https://raw.githubusercontent.com/bguedes/static/master/restGetAddingProduct.png)

![](https://raw.githubusercontent.com/bguedes/static/master/restGetAddingProductResult.png)

### Delete a specific product

![](https://raw.githubusercontent.com/bguedes/static/master/restDeleteProduct.png)

![](https://raw.githubusercontent.com/bguedes/static/master/restDeleteProductResult.png)

### View a specific product

![](https://raw.githubusercontent.com/bguedes/static/master/restGetProduct.png)

![](https://raw.githubusercontent.com/bguedes/static/master/restGetProductResult.png)
