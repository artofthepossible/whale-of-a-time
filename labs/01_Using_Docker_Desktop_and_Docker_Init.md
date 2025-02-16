### Using Docker Desktop and the Docker Init Command Lab

Overview
In this lab, you'll learn 
The fundamentals docker init CLI commands to kickstart your containerization process, understand the inputs and outputs.
Learn how to mount a volume in a Docker container to persist data and share files between the host and the container.
Guide you through the process of optimizing your Java application running in a Docker container by modifying Java arguments and entrypoints.

Time to Complete: 5-30 minutes

### How to Use This Hands-On Lab
1. You have installed the latest version of Docker Desktop.
2. You have installed a Git client.
3. You have an IDE or a text editor to edit files. Docker recommends using Visual Studio Code.
4. Clone the getting-started-app repository using the following command:
```sh
$ git clone https://github.com/artofthepossible/whale-of-a-time
```


### Overview
Creates Docker-related starter files for your project
Supplies .dockerignore, Dockerfile, and compose.yaml files
Has support for ASP.NET, Java, Go, Node, Python, and Rust


### Steps
### Part 1: Docker Init

```sh
$ docker init
```
Follow prompts

```sh
What application platform does your project use? Java
What’s the relative directory (with a leading .) for your app? ./src
What version of Java do you want to use? 21
What port does your server listen on? 8080
```

The command expedites the creation of necessary Docker assets which accelerates the process to dockerize an application saving you time and allowing you to do more</br>

Review the outputs of the artifacts created</br>
Depending on your application, make updates to the multistage dockerfile shared based on your application needs</br>
.dockerignore (a config file that describes files and directories that you want to exclude when building a Docker image)</br>
Dockerfile (a list of commands used to assemble an image.), </br>
compose.yaml ( a file to define multiple container applications)</br>
and  README file </br>

### Part 2: Start your application

```sh
docker compose up --build
```
In your terminal you can use the following commands to bring up Docker Desktop</br>
```sh
$ v View in Docker Desktop
```
**Access the Application**</br>
Your application will be available at http://localhost:8080

### Part 3: Stop your application 

```sh
docker compose down
```

### Part 4: Mounting a Volume in a Docker Container
Objective: Learn how to mount a volume in a Docker container to persist data and share files between the host and the container.</br>

To enhance your existing Spring Boot application to allow an end user to mount a volume for logs, you can follow these steps:</br>

This setup ensures that logs written to /tmp/logs inside the container are persisted on the host machine in the ./logs directory.</br>

Dockerfile: Add VOLUME /tmp/logs to define the volume.</br>
Docker Compose: Map the volume in docker-compose.yml to link the host directory to the container directory.</br>


Modify the Application Properties</br>

Update your application.properties file to specify the log file location. </br>This will allow you to direct logs to a specific directory that can be mounted as a volume.</br>

```sh
# filepath: /whale-of-a-time/src/main/resources/application.properties
logging.file.path=/tmp/logs
```
To include a volume for /tmp/logs in your Dockerfile and Docker Compose file, you can follow these steps:</br>

**Dockerfile Update**</br>
Add the VOLUME /tmp/logs instruction to your Dockerfile. This will create a mount point with the specified path and mark it as </br>holding externally mounted volumes from native host or other containers.</br>
```sh
# ...existing code...

FROM eclipse-temurin:21-jre-jammy AS final

# Create a non-privileged user that the app will run under.
# See https://docs.docker.com/go/dockerfile-user-best-practices/
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable from the "package" stage.
COPY --from=extract build/target/extracted/dependencies/ ./
COPY --from=extract build/target/extracted/spring-boot-loader/ ./
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract build/target/extracted/application/ ./

# Define the volume
VOLUME /tmp/logs

EXPOSE 8080

ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]
```

**Docker Compose File Update**</br>
Update your docker-compose.yml file to include the volume definition and mount it to the container.
```sh
services:
  app:
    build: .
    ports:
      - "8080:8080"
    volumes:
      - ./logs:/tmp/logs
```


### Part 5: Optimizing Java Application
This section will guide you through the process of optimizing your Java application running in a Docker container by modifying Java arguments and entrypoints.</br>
By adjusting environment variables and JVM options, you can enhance the performance and behavior of your application.</br>

**Entrypoint**</br>
Java arguments and entrypoints are configurations that control how your Java application runs inside a Docker container. </br>These configurations include setting heap sizes, garbage collection options, and other JVM parameters that can impact the performance and behavior of your application.</br>


**Why**</br>
Optimizing Java arguments and entrypoints can provide several benefits:

**Performance**: Properly configured JVM options can improve the performance of your application by optimizing memory usage and garbage collection.</br>
**Resource Management**: Setting appropriate heap sizes ensures that your application uses the right amount of memory, preventing out-of-memory errors and reducing resource wastage.</br>
**Stability**: Fine-tuning JVM options can lead to a more stable application by avoiding common pitfalls such as long garbage collection pauses.</br>

Follow these steps to optimize your Java application:</br>

Set Environment Variables for Java Options: Add the ENV JAVA_OPTS line to your Dockerfile to set useful Java options such as initial and maximum heap size, garbage collector, and entropy source.</br>

Define the Entrypoint: Modify the ENTRYPOINT to include the JAVA_OPTS environment variable, allowing you to pass JVM arguments when starting the application.</br>

```sh
# syntax=docker/dockerfile:1

# Comments are provided throughout this file to help you get started.
# If you need more help, visit the Dockerfile reference guide at
# https://docs.docker.com/go/dockerfile-reference/

# Want to help us make this template better? Share your feedback here: https://forms.gle/ybq9Krt8jtBL3iCk7

################################################################################

# Create a stage for resolving and downloading dependencies.
FROM eclipse-temurin:21-jdk-jammy AS deps

WORKDIR /build

# Copy the mvnw wrapper with executable permissions.
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.m2 so that subsequent builds don't have to
# re-download packages.
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

################################################################################

# Create a stage for building the application based on the stage with downloaded dependencies.
# This Dockerfile is optimized for Java applications that output an uber jar, which includes
# all the dependencies needed to run your app inside a JVM. If your app doesn't output an uber
# jar and instead relies on an application server like Apache Tomcat, you'll need to update this
# stage with the correct filename of your package and update the base image of the "final" stage
# use the relevant app server, e.g., using tomcat (https://hub.docker.com/_/tomcat/) as a base image.
FROM deps AS package

WORKDIR /build

COPY ./src src/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################################################################################

# Create a stage for extracting the application into separate layers.
# Take advantage of Spring Boot's layer tools and Docker's caching by extracting
# the packaged application into separate layers that can be copied into the final stage.
# See Spring's docs for reference:
# https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html
FROM package AS extract

WORKDIR /build

RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

################################################################################

# Create a new stage for running the application that contains the minimal
# runtime dependencies for the application. This often uses a different base
# image from the install or build stage where the necessary files are copied
# from the install stage.
#
# The example below uses eclipse-turmin's JRE image as the foundation for running the app.
# By specifying the "21-jre-jammy" tag, it will also use whatever happens to be the
# most recent version of that tag when you build your Dockerfile.
# If reproducibility is important, consider using a specific digest SHA, like
# eclipse-temurin@sha256:99cede493dfd88720b610eb8077c8688d3cca50003d76d1d539b0efc8cca72b4.
FROM eclipse-temurin:21-jre-jammy AS final

# Create a non-privileged user that the app will run under.
# See https://docs.docker.com/go/dockerfile-user-best-practices/
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable from the "package" stage.
COPY --from=extract build/target/extracted/dependencies/ ./
COPY --from=extract build/target/extracted/spring-boot-loader/ ./
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract build/target/extracted/application/ ./

# Define the volume
VOLUME /tmp/logs

# Expose the application port
EXPOSE 8080

# Set environment variables for Java options
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

# Define the entrypoint with Java options
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher" ]
```
```sh
docker compose up --build -d
```

**Access the Application**</br>
Your application will be available at: 

```sh
http://localhost:8080
```

### Best Practices

**Avoid Running as Root User**</br>
Running containers as the root user can pose security risks. Ensure that your Dockerfile creates and uses a non-privileged user to run the application.</br>

**No Secrets/Credentials/Keys in Dockerfile**</br>
Avoid hardcoding secrets, credentials, or keys in your Dockerfile. Use environment variables or Secrets Managers to manage sensitive information securely.</br>

**Use Docker Scout to Assess Image for Vulnerabilities**</br>
Docker Scout helps you identify and fix vulnerabilities in your Docker images. Use the following command to scan your image for vulnerabilities:</br>

**Access the Application**</br>
Navigate to the resource usage extenstion to Monitor and manage live data stream for running containers.</br>

To simulate some traffic to the container</br>
This command will continuously send requests to the application every second.</br>

```sh
while true; do curl http://localhost:8080/; sleep 1; done
```

**Stop your Application**</br>
```sh
docker compose down
```
