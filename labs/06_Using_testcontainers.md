### Using Testcontainers

Overview
In this lab, you'll learn the fundamentals of testcontainers and how to setup testcontainers cloud</br>

Testcontainers is an opensource framework for providing lightweight, throwaway </br>instances of common databases, Selenium web browsers, or anything else that can run </br>in a Docker container</br>

With Testcontainers cloud, we are helping developers to improve their code quality by providing them </br>
- Ephemeral, scalable test resources </br>
- Consistent environments across teams </br>
- Faster feedback cycles and reduced flaky tests </br>

At a high level, Testcontainers Cloud lets development teams burst into the cloud to be able to run tests at scale</br>
In an infrastructure as code manner, They get to spin up unit and integration tests  </br>
using their domain specific language and execute these in parallel</br>


Time to Complete: 15-20 minutes

### How to Use This Hands-On Lab

1. To get started with Testcontainers Cloud, you need to completed the following:</br>
a. Testcontainers Cloud Account: https://app.testcontainers.cloud/
b.Install Java 17 or newer</br>
You'll need Java 17 or newer for this workshop. Testcontainers libraries are compatible with Java 8+, but this workshop uses a Spring Boot 3.x application which requires Java 17 or newer. </br>

c. Maven 3.6.0 or higher</br>

d. Login to docker hub docker login -u yourdockerid. Use the (personal access token)[https://www.docker.com/blog/docker-hub-new-personal-access-tokens/]</br>
```sh
docker login -u yourdockerid 
yourpat
```
e. Set you environmnet variables</br>
export DH_USERNAME=yourdockerid </br>
export DH_oPAT=dckr_pat_yourpat </br>

### Part 1: Add Unit Tests
Generate a unit test using Testcontainers that validates the text "Welcome to My Spring Boot Application" and "Get it on GitHub" are found in the UI of your Spring Boot application

Here's how you can set it up:
1. Add the necessary dependencies to your pom.xml:

```sh
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.4.0-SNAPSHOT</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>whale-of-a-time</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>whale-of-a-time</name>
	<description>unbox and containerize your Spring Boot app</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.testcontainers</groupId>
			<artifactId>testcontainers</artifactId>
			<version>1.16.0</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.testcontainers</groupId>
			<artifactId>junit-jupiter</artifactId>
			<version>1.16.0</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.jsoup</groupId>
			<artifactId>jsoup</artifactId>
			<version>1.15.3</version>
		</dependency>

	</dependencies>
	
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

   ```
2. Create a unit test class using Testcontainers
This test does the following:

Uses Testcontainers to start the Spring Boot application and a Chrome browser container.
Navigates to the application's URL.
Validates that the text "Welcome to My Spring Boot Application" and the link "Get it on GitHub" are present in the UI.
Make sure to replace "demonstrationorg/whale-of-a-time-alpaquita-liberica-openjdk-alpine:v1.0" with the actual image name of your Spring Boot application.


   ```sh
package com.example.whale_of_a_time;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WhaleOfATimeApplicationTests {

    @LocalServerPort
    private int port;

    @Container
    public static GenericContainer<?> app = new GenericContainer<>("demonstrationorg/whale-of-a-time-alpaquita-liberica-openjdk-alpine:v1.0")
            .waitingFor(Wait.forHttp("/"))
            .withExposedPorts(8080)
            .withCommand("java", "-jar", "/app/app.jar");

    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        baseUrl = "http://" + app.getHost() + ":" + app.getMappedPort(8080);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testHomePage() throws IOException {
        String baseUrl = WhaleOfATimeApplicationTests.baseUrl;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(baseUrl, String.class);

        // Parse the HTML response using Jsoup
        org.jsoup.nodes.Document doc = Jsoup.parse(response);

        // Assert that the title contains
        assertThat(doc.body().text()).contains("Welcome to My Spring Boot Application");
        assertThat(doc.body().text()).contains("Get it on GitHub");
    }
}

   ```

### Part 2: Run Unit Tests using Testcontainers Cloud
Build the application by running the command mvn clean install then you can simply use the following mvn spring-boot:run to test the application.

1. Build the Application
   ```sh
    mvn clean install
   ```
2. Run the Application and Tests
   ```sh
    mvn spring-boot:run
   ```
The application will start and be accessible at http://localhost:8080/.
   ```sh
   	curl http://localhost:8080/
   ```
3. Access the Endpoint
Open your web browser and navigate to:
   ```sh
    http://localhost:8080/
   ```
You should see the text "Welcome to My Spring Boot Application".

4. Running Tests, open a separate terminal and invoke the following command
The application includes unit tests that use the Testcontainers library to validate the appearance of the text "Welcome to My Spring Boot Application" when the application starts.

To run the tests, use the following command:
   ```sh
    mvn test
   ```

Tests are executed locally wihtout any failures

   ```sh
    [INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.278 s -- in com.example.whale_of_a_time.WhaleOfATimeApplicationTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  6.696 s
[INFO] Finished at: 2025-01-08T17:57:04-05:00
[INFO] ------------------------------------------------------------------------
   ```