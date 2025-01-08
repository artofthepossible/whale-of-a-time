### Introduction to Docker Build Cloud Lab

Overview
In this lab, you'll learn the fundamentals of docker build cloud.

Time to Complete: 15-20 minutes

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
Cloud-based builders let developers build faster especially when they’re working on older machines.
A pair of AMD and ARM builders help developers perform multi-architecture builds without spending hours configuring and rebuilding for emulators.
A shared cache empowers teams working on the same repo to cut wait times by avoiding duplicative work.


### Part 1: Build Image using Docker Build Cloud
1. Open a terminal in the root directory of your project.

2. Run the following command to build the Docker image using *Docker Build Cloud*
   ```sh
   $ docker buildx build --builder your-dbc-builder your-dh-org/your-image-name:tag 
   ```
   Example: 
   docker buildx build --builder cloud-demonstrationorg-default -t demonstrationorg/demo-whale-of-a-time:v1.0 .

### Part 2: Build Image using Default Builder
3. Run the following command to build the Docker image using the *default builder*

    ```sh
   $ docker build -t demonstrationorg/whale-of-a-time:v1.0 .
   ```
### Part 3: Review images built with Docker Build Cloud versus default Builder
3. Verify the images via docker desktop by Viewing the Build Details in Docker Desktop
   ```sh
   On the left hand menu, Select Builds the Build History
   For each Build History Item, rewivew the Build History Details
   ```
### Part 4: Base Images

5. Updating the Base Image
For most cases, you don't need to create your own base image. Docker Hub contains a vast library of Docker images that are suitable for use as a base image in your build. 
Docker Official Images are specifically designed as a set of hardened, battle-tested images that support a wide variety of platforms, languages, and frameworks. 
There are also Docker Verified Publisher images, created by trusted publishing partners, verified by Docker.

6. Discussion
Identify some base images that you want to use for your application and make some changes in your dockerfile
1. Update the Dockerfile for a sample application.
2. Build the image with an appropriate tag.
3. Tag the image with a semantic version.
4. Push the image to Docker Hub.

7. Spend time reviewing images built (size and packages).

Existing base image
```sh
FROM eclipse-temurin:21-jre-jammy 
```
7a. What happens if you switch to another base image to something like this:
```sh
FROM bitnami/java:21
```

7b. What happens if you switch the base image to something like this:
```sh
FROM bellsoft/liberica-openjdk-alpine:21
```

8. What other ways can we optimize the base images and packages

9. Using docker desktop and reviewing the base image how does the size and number of packages shift?

10. Which base images results in less vulnerabilities?


### Part 5: Reviewing Built Images with the Builds Tab

1. Open Docker Desktop:
   - Launch Docker Desktop from your applications menu.
  
2. Navigate to the Builds Tab:
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
   - The "Logs" section contains the build logs, which provide detailed information about each step of the build process. 
   You can see the output of each command executed during the build.


7. Walkthrough of Build Timing:
```sh
   - In the "History" section (if available), you can see the timing for each step of the build process. This helps you identify which steps took the longest and whether they were cached or not.</br>
   - Cached steps are typically faster because Docker reuses the existing layers instead of rebuilding them from scratch.</br>
```
### Part 6: Attestations
Build attestations give you detailed information about how an image was built and what it contains. </br>
These attestations, generated by BuildKit during build-time, attach to the final image as metadata, allowing you to inspect an image to see its origin, creator, and contents. </br>
This information helps you make informed decisions about the security and impact of the image on your supply chain.</br>

There are two key types of attestations:</br>

SBOM, which lists the software artifacts within the image.</br>
Provenance, which details how the image was built.</br>


8. Adding `--sbom=true` and `--provenance=true` to Build Commands:
```sh
   - When you add `--sbom=true` and `--provenance=true` to your build commands, Docker includes additional metadata in the resulting image. 
   - `--sbom=true` generates a Software Bill of Materials (SBOM) that lists all the components and dependencies included in the image.</br>
   - `--provenance=true` adds provenance information, which provides details about the build environment and process, ensuring the integrity and authenticity of the image.</br>
```
9. Example build command with `--sbom=true` and `--provenance=true`:

   ```sh
   docker build -t demonstrationorg/whale-of-a-time-alpaquita-liberica-openjdk-alpine:v1.0 --sbom=true --provenance=true .
   ```

10. This command specifies the use of the faster builder Docker build cloud: cloud-demonstrationorg-default builder and includes the --sbom and --provenance flags for generating SBOM and provenance information.

 ```sh
   docker buildx build --builder cloud-demonstrationorg-default --sbom=true --provenance=true -t  demonstrationorg/whale-of-a-time-alpaquita-liberica-openjdk-alpine:v1.0 .
```

11. Review the History Section:
   - Walkthrough of Build Timing for an image with historical details </br>
   - In the "History" section, you can see the timing for each step of the build process. This helps you identify which steps took the longest and whether they were cached or not.</br>
   - Cached steps are typically faster because Docker reuses the existing layers instead of rebuilding them from scratch.</br>
```sh
   - The "History" section shows a chronological list of all the layers and commands that were executed to create the image. 
   This includes both cached and non-cached steps.
```

### Part 7:  Test Locally
1. When you're ready, start your application, Run the container locally: 
   ```sh 
   docker compose up --build
   ```


2. Access the application at http://localhost:8080
   ```sh 
   curl localhost:8080
   ```


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


