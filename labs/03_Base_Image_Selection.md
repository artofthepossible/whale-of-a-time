### Using Base Image Selection Lab

Overview
In this lab, you'll learn
How to work with Multistage builds and optimize containers.
How to update base images, make changes, and rebuild the resulting image in Docker locally as well as with Docker Build Cloud:

Time to Complete: 15-20 minutes

Overivew
This Dockerfile is a multi-stage file designed to create a lightweight, production-ready container for a Spring Boot application. It uses multiple stages to optimize the build process, reduce the final image size, and improve caching. 
Here's a detailed breakdown of each stage:
```sh
FROM eclipse-temurin:21-jdk-jammy AS deps
```
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#from)

Starts the first stage named deps using the Eclipse Temurin JDK 21 base image. This stage is used to prepare dependencies for the build.
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#workdir)
```sh
WORKDIR /build
```
Sets the working directory to /build for subsequent commands in this stage.
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#workdir)
```sh
COPY .mvn/ .mvn/
```

Copies the .mvn directory (used by the Maven wrapper) into the container.
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#copy)
```sh
RUN --mount=type=bind,source=pom.xml,target=pom.xml,readonly \
--mount=type=cache,target=/root/.m2 \
./mvnw dependency:go-offline -DskipTests
```
Runs the Maven wrapper to download all dependencies required for the project in offline mode.
The --mount option is used to mount files or directories from the host machine into the container. In this case, it specifies a bind mount, which means a file or directory on the host machine is mounted into the container at a specified location. The type=bind indicates that this is a bind mount.

The source=pom.xml part specifies the source file on the host machine that will be mounted. The target=pom.xml part specifies the target location inside the container where the source file will be mounted. By using the same name for both source and target, the pom.xml file from the host is directly accessible as pom.xml inside the container.

The readonly option makes the mounted file read-only inside the container. This means that any processes running inside the container can read the pom.xml file but cannot modify it. This is useful for ensuring that the file remains unchanged during the build process, which can help maintain consistency and prevent accidental modifications.
```sh
--mount=type=bind: Mounts the pom.xml file as read-only to avoid copying it into the image.
--mount=type=cache: Caches Maven dependencies in /root/.m2 to speed up subsequent builds.
```
dependency:go-offline: Downloads all dependencies without running tests.
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#run)

```sh
FROM deps AS package
```
Starts a new stage named package, inheriting from the deps stage. This stage builds the application.
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#from)
```sh
WORKDIR /build
```
Sets the working directory to /build for this stage.
[See documentation][⁠(](https://docs.docker.com/reference/dockerfile/#workdir))
```sh
RUN --mount=type=bind,source=pom.xml,target=pom.xml,readonly \
--mount=type=cache,target=/root/.m2 \
./mvnw package -DskipTests && \
mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar
```

Builds the application using Maven

The mvnw package command compiles the code and packages it into a JAR file.
The mv command renames the generated JAR file to app.jar for consistency.
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#run)

```sh
FROM deps AS package
```
Starts a new stage named package, inheriting from the deps stage. This stage builds the application.
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#from)
```sh
FROM package AS extract
```
Starts a new stage named extract, inheriting from the package stage. This stage extracts the Spring Boot JAR layers.

[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#from)
```sh
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted
```
Uses the Spring Boot layertools feature to extract the JAR file into layers. This allows for better caching and smaller image layers in the final stage.

[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#run)

```sh
FROM eclipse-temurin:21-jre-jammy AS final
```
Starts the final stage named final using the Eclipse Temurin JRE 21 base image. This stage is optimized for running the application.

```sh
ARG UID=10001
```
Defines a build-time argument UID with a default value of 10001. This is used to create a non-root user.

[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#run)
```sh
RUN adduser \
--disabled-password \
--gecos "" \
--home "/nonexistent" \
--shell "/sbin/nologin" \
--no-create-home \
--uid "${UID}" \
appuser
```
Creates a non-root user named appuser with the specified UID. The user has no home directory, no login shell, and no password.

See documentation⁠
```sh
USER appuser
```
Switches to the appuser user for running the application, improving security.

[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#user)

```sh
COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./
```
Copies the extracted JAR layers (dependencies, Spring Boot loader, snapshot dependencies, and application code) from the extract stage into the final image.
[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#copy)

EXPOSE 8080

Documents that the application listens on port 8080. This does not actually publish the port; it is for informational purposes.

[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#expose)
ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]

Sets the default command to run the application using the Spring Boot JarLauncher.

[See documentation]⁠(https://docs.docker.com/reference/dockerfile/#entrypoint)

### How to Use This Hands-On Lab

### Steps

### Part 1. Update the base image, Make Changes and Rebuild your image
1. Pin your base image to a specific version using a digest to ensure consistency</br>
Regularly update your base image to get the latest security patches and updates</br>

2. Make changes to your application:</br>
Update your source code files as needed.</br>
If using bind mounts for development, changes will be reflected immediately in the container</br>

Spend time reviewing images built (size and packages) and consider changes to the Existing base image</br>
```sh
FROM eclipse-temurin:21-jre-jammy 
```

3a. What happens if you switch the base image to something like this:</br>
```sh
FROM bellsoft/liberica-openjdk-alpine:21
```
or 
```sh
FROM eclipse-temurin:23-jdk-alpine-3.21
```
3c. What other ways can we optimize the base images and packages</br>


3d. Using docker desktop and reviewing the base image how does the size and number of packages shift?</br>

3e. Which base images results in less vulnerabilities?</br>

4. For each base image change, Rebuild the image:</br>
Use the docker build command to rebuild your image with the changes:</br>
```sh
docker build -t your-image-name:tag .
docker build -t demonstrationorg/whale-of-a-time-dbc:v1.0 .
docker build -t demonstrationorg/whale-of-a-time-dbc-alpaquita:v1.0 .
```

To ensure you're getting the latest versions of dependencies, use the --no-cache option:</br>

```sh
docker build --no-cache -t your-image-name:tag .
docker build --no-cache -t demonstrationorg/whale-of-a-time-dbc-alpaquita:v1.0 .
```

4. Test the new image:</br>
Run a container from the new image to test your changes:</br>
```sh
docker run -dp 127.0.0.1:8080:8080 your-image-name:tag
docker run -dp 127.0.0.1:8080:8080 demonstrationorg/whale-of-a-time-dbc-alpaquita:v1.0

```
Verify that your application works as expected with the changes.</br>

5. Push the updated image:</br>
If you're satisfied with the changes, push the new image to your registry (e.g., Docker Hub):</br>

```sh
docker push your-username/your-image-name:tag
docker push demonstrationorg/whale-of-a-time:v1.0
docker push demonstrationorg/whale-of-a-time-dbc-alpaquita:v1.0
```
### Part 2: Reviewing images composition of Built Images with the Builds Tab</br>
Verify the images via docker desktop by Viewing the Build Details in Docker Desktop</br>
1. Launch Docker Desktop from your applications menu and Navigate to the Builds Tab:</br>
```sh
   - Click on the "Builds" tab in Docker Desktop to see a list of your recent builds.

3. Select a Built Image:
```sh
   - Click on the image you want to review. This will open the details view for that image.
```
4. Review the Info Section:
```sh
   - The "Info" section provides general information about the image, such as the image ID, size, and creation date.
```
5. Review the Source Section:
```sh
   - The "Source" section shows the Dockerfile or build configuration used to create the image.
   This helps you understand the steps and commands used during the build process. 
```
6. Review the Logs Section:
```sh
   - The "Logs" section contains the build logs, which provide detailed information about each step of the build process. </br>
   You can see the output of each command executed during the build.
```

7. Walkthrough of Build Timing:
```sh
   - In the "History" section (if available), you can see the timing for each step of the build process. This helps you identify which steps took the longest and whether they were cached or not.</br>
   - Cached steps are typically faster because Docker reuses the existing layers instead of rebuilding them from scratch.</br>
```

8. Review the History Section:
   - Walkthrough of Build Timing for an image with historical details </br>
   - In the "History" section, you can see the timing for each step of the build process. This helps you identify which steps took the longest and whether they were cached or not.</br>
   - Cached steps are typically faster because Docker reuses the existing layers instead of rebuilding them from scratch.</br>
```sh
   - The "History" section shows a chronological list of all the layers and commands that were executed to create the image. </br>
   This includes both cached and non-cached steps.</br>
```

### Part 3: Attestations
Build attestations give you detailed information about how an image was built and what it contains. </br>
These attestations, generated by BuildKit during build-time, attach to the final image as metadata, allowing you to inspect an image to see its origin, creator, and contents. </br>
This information helps you make informed decisions about the security and impact of the image on your supply chain.</br>

There are two key types of attestations:</br>
Software bill of materials (SBOM), which lists the software artifacts within the image.</br>
Provenance, which details how the image was built.</br>

1. Adding `--sbom=true` and `--provenance=true` to Build Commands:
```sh
   - When you add `--sbom=true` and `--provenance=true` to your build commands, Docker includes additional metadata in the resulting image. </br>
   - `--sbom=true` generates a Software Bill of Materials (SBOM) that lists all the components and dependencies included in the image.</br>
   - `--provenance=true` adds provenance information, which provides details about the build environment and process, ensuring the integrity and authenticity of the image.</br>
```
2. Example build command with `--sbom=true` and `--provenance=true`:

   ```sh
   docker build -t demonstrationorg/whale-of-a-time-dbc-alpaquita:v1.0 --sbom=true --provenance=true .
   ```

3. This command specifies the use of the faster builder Docker build cloud: cloud-demonstrationorg-default builder and includes the --sbom and --provenance flags for generating SBOM and provenance information.

 ```sh
   docker buildx build --builder cloud-demonstrationorg-default --sbom=true --provenance=true -t  demonstrationorg/whale-of-a-time-dbc-alpaquita:v1.0 .
```


### Part 4:  Docker Build Cloud and Test Locally
DBC builders let developers build faster especially when they’re working on older machines.
A pair of AMD and ARM builders help developers perform multi-architecture builds without spending hours configuring and rebuilding for emulators.
A shared cache empowers teams working on the same repo to cut wait times by avoiding duplicative work.

1. To get started with Docker Build Cloud, you need to:
Create the Docker Build Cloud builder in the [Docker Build Cloud Dashboard](https://app.docker.com/build/).
Note: Multiple cloud builders for a single account is in early access. To be one of the first teams to have a mutli-builder experience, join the waitlist!

2. You can add a cloud builder using the CLI
```sh
docker login
```

3. Connect to your builder and set it as the default builder
```sh
$ docker buildx use cloud-ORG-BUILDER_NAME --global 
```
Example: docker buildx use cloud-demonstrationorg-default --global

Replace ORG with the Docker Hub namespace of your Docker organization.

1. When you're ready, start your application, Run the container locally: 
   ```sh 
   docker compose up --build
   ```
2. Access the application at http://localhost:8080
   ```sh 
   curl localhost:8080
   ```


### Extra Lab: Build Image using Docker Build Cloud
Note! Developers can Build Image using Default Builder or use Docker Build Cloud Builder

1. Open a terminal in the root directory of your project.

2. Run the following command to build the Docker image using *Docker Build Cloud*
   ```sh
   $ docker buildx build --builder your-dbc-builder your-dh-org/your-image-name:tag 
   ```
   Example: 
   docker buildx build --builder cloud-demonstrationorg-default -t whale-of-a-time-dbc-alpaquita:v1.0:v1.0 .

3. Clean up:
Remove old containers and images that are no longer needed to free up space.
Remember to regularly rebuild your images to incorporate security updates and new dependencies. 
You can also use Docker Scout to get recommendations for improving your image security and updating base images Image details view.


### Resources
https://docs.docker.com/build-cloud/

First, build your image, e.g.: `docker build -t myapp .`.

If your cloud uses a different CPU architecture than your development
machine (e.g., you are on a Mac M1 and your cloud provider is amd64),
you'll want to build the image for that platform, e.g.:
`docker build --platform=linux/amd64 -t myapp .`.

Then, push it to your registry, e.g. `docker push myregistry.com/myapp`.
Consult Docker's [getting started](https://docs.docker.com/go/get-started-sharing/)
docs for more detail on building and pushing.


