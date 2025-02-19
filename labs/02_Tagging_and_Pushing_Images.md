### Tagging and Pushing Images Lab

Overview
In this lab, you'll learn the following
How to Build, tag, and publish a Docker image following best practices.
Why Proper tagging is crucial for version control and deployment.
Adhering to best practices enhances collaboration and reduces errors.

Time to Complete: 5-10 minutes

### How to Use This Hands-On Lab

### Steps

### Part 1: Tagging Overview (Existing Image)

1. Find the existing image that we built in previous la by running the command 
```sh
docker image ls
```

2. Tag the existing image [whale-of-a-time-server] with a semantic version.
docker tag existing-image:tagname new-image:tagname

```sh
docker tag whale-of-a-time-server:latest ORG_NAME/IMAGE_NAME:v1.0

Example $ docker tag whale-of-a-time-server:latest demonstrationorg/whale-of-a-time:v1.0
```
Example:docker tag whale-of-a-time-server:latest demonstrationorg/whale-of-a-time:v1.0</br>
Replace ORG with the Docker Hub namespace of your Docker organization.</br>

3. Confirm you have a new image tag by running the command below
```sh
docker image ls
```

```sh
Expected Outputs
REPOSITORY                                                             TAG                                                                           IMAGE ID       CREATED             SIZE
demonstrationorg/whale-of-a-time                                       v1.0                                                                          72ea28c44fca   About an hour ago   433MB
whale-of-a-time-server                                                 latest                                                                        72ea28c44fca   About an hour ago   433MB
```

4. Push the image to Docker Hub.
```sh
docker push ORG_NAME/IMAGE_NAME:vtagname
```
Example:docker push demonstrationorg/whale-of-a-time:v1.0</br>
Replace ORG with the Docker Hub namespace of your Docker organization.</br>

**Extra**</br>
To tag and push a Docker image in one command, you can use the docker tag and docker push commands in a single line using the && operator. </br>

Here is the command:</br>
```sh
docker tag whale-of-a-time-server:latest ORG_NAME/IMAGE_NAME:v1.0 && docker push ORG_NAME/IMAGE_NAME:v1.0
```
Replace ORG_NAME with your Docker Hub organization name and IMAGE_NAME with the desired image name.</br>

### Resources:

Refer to the official Docker documentation for detailed guidance:
[Build, tag, and publish an image](https://docs.docker.com/get-started/docker-concepts/building-images/build-tag-and-publish-an-image/)
[Docker Tagging Best Practices] (https://www.docker.com/blog/docker-best-practices-using-tags-and-labels-to-manage-docker-image-sprawl/)