### Introduction to Docker Build Cloud Lab

Overview
In this lab, you'll learn the fundamentals docker build cloud.

Time to Complete: 20-30 minutes

### How to Use This Hands-On Lab
1. To get started with Docker Build Cloud, you need to:
Create the Docker Build Cloud builder in the [Docker Build Cloud Dashboard](https://app.docker.com/build/).
Note: Multiple cloud builders for a single account is in early access. To be one of the first teams to have a mutli-builder experience, join the waitlist!

2. You can add a cloud builder using the CLI
```sh
$ docker login
```

3. Connect to your builder and set it as the default builder
```sh
$ docker buildx use cloud-ORG-BUILDER_NAME --global 
```
Example: docker buildx use cloud-demonstrationorg-default --global

Replace ORG with the Docker Hub namespace of your Docker organization.


### Overview
Creates Docker-related starter files for your project
Supplies .dockerignore, Dockerfile, and compose.yaml files
Has support for ASP.NET, Java, Go, Node, Python, and Rust

1. Open a terminal in the root directory of your project.

2. Run the following command to build the Docker image using Docker Build Cloud:
   ```sh
   $ docker buildx build --builder your-dbc-builder your-dh-org/your-image-name:tag 
   ```
   Example: docker buildx build --builder cloud-demonstrationorg-default demonstrationorg/whale-of-a-time:v1.0

   ```sh
   $ docker buildx build --builder cloud-demonstrationorg-default demonstrationorg/workshop-prep-demo-alpaquita:v1.0 
   ```
   Example: docker buildx build --builder cloud-demonstrationorg-default --tag demonstrationorg/whale-of-a-time:v1.0 .
  

    ```sh
   $ docker build -t demonstrationorg/whale-of-a-time:v1.0 .
   ```

3. Verify the images via docker desktop by navigate to docker desktop> click images 


4. To verify via cli, Run the command via terminal:
   ```sh
   $ docker images
   ```

5. Base Images
Discussion:
Spend time reviewing images built (size and packages).
Existing base image
```sh
FROM eclipse-temurin:21-jre-jammy 
```
6. 7. What happens if you switch the base image to something like this:
```sh
FROM bellsoft/liberica-native-image-kit-container
```
8. What other ways can we optimzie the base images and packages

9. Using docker desktop and reviewing the base image how does the size and number of packages shift?

10. Which base images results in less vulnerabilities?

Temurin - eclipse-temurin:21-jdk-jammy: 
Size: 413.95 MB: 221 packages and 0 Critical and High CVEs 

Alpaquita - bellsoft/liberica-openjdk-alpine:21: 
Size: 227.63 MB: 38 packages and 0 Critical and High CVEs

Bitnami - bitnami/java:21: 
Size: 981.07 MB: 197 packages and 0 Critical and High CVEs


# Reviewing Built Images with the Builds Tab

1. Open Docker Desktop:
   - Launch Docker Desktop from your applications menu.

2. Navigate to the Builds Tab:
   - Click on the "Builds" tab in Docker Desktop to see a list of your recent builds.

3. Select a Built Image:
   - Click on the image you want to review. This will open the details view for that image.

4. Review the Info Section:
   - The "Info" section provides general information about the image, such as the image ID, size, and creation date.

5. Review the Source Section:
   - The "Source" section shows the Dockerfile or build configuration used to create the image. This helps you understand the steps and commands used during the build process.

6. Review the Logs Section:
   - The "Logs" section contains the build logs, which provide detailed information about each step of the build process. You can see the output of each command executed during the build.

7. Review the History Section:
   - The "History" section shows a chronological list of all the layers and commands that were executed to create the image. This includes both cached and non-cached steps.

8. Walkthrough of Build Timing:
   - In the "History" section, you can see the timing for each step of the build process. This helps you identify which steps took the longest and whether they were cached or not.
   - Cached steps are typically faster because Docker reuses the existing layers instead of rebuilding them from scratch.

9. Adding `--sbom=true` and `--provenance=true` to Build Commands:
   - When you add `--sbom=true` and `--provenance=true` to your build commands, Docker includes additional metadata in the resulting image.
   - `--sbom=true` generates a Software Bill of Materials (SBOM) that lists all the components and dependencies included in the image.
   - `--provenance=true` adds provenance information, which provides details about the build environment and process, ensuring the integrity and authenticity of the image.

10. Example build command with `--sbom=true` and `--provenance=true`:

   ```sh
   docker build -t demonstrationorg/workshop-prep-demo-alpaquita:v1.0 --sbom=true --provenance=true .
   ```


11. This command specifies the use of the faster builder Docker build cloud: cloud-demonstrationorg-default builder and includes the --sbom and --provenance flags for generating SBOM and provenance information.

 ```sh
   docker buildx build --builder cloud-demonstrationorg-default --sbom=true --provenance=true -t demonstrationorg/workshop-prep-demo-alpaquita:v1.0 .
```

12. Walkthrough of Build Timing:
   - In the "History" section, you can see the timing for each step of the build process. This helps you identify which steps took the longest and whether they were cached or not.
   - Cached steps are typically faster because Docker reuses the existing layers instead of rebuilding them from scratch.

# Test Locally
1. Run the container locally: 
   ```sh 
   docker compose up --build
   ```


b. Alternate command to run the container: 
   ```sh 
   docker run -p 8080:8080 spring-boot-app:1.0
   ```


2. Access the application at http://localhost:8080
   ```sh 
   curl localhost:8080
   ```

### Resources
https://docs.docker.com/build-cloud/