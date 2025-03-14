### Tagging and Pushing Images Lab

**Overview**
In this lab, you'll learn the following
How to Build, tag, and publish a Docker image following best practices.
Why Proper tagging is crucial for version control and deployment.
Adhering to best practices enhances collaboration and reduces errors.

**Using Tags:**
Proper use of tags helps reduce confusion, ensures better documentation, and prevents production issues caused by image sprawl.</br>

Tags help identify and organize Docker images. Semantic versioning (e.g., v1.0.0) is recommended for clarity.</br>

The latest tag is often misunderstood; it simply points to the most recently pushed image without a specific tag, not necessarily the most updated version. Instead consider Use Semantic Versioning. Use the commit SHA for tagging images in your CI workflow. This ensures each image in your registry can be directly linked to its corresponding source code commit.  SHA digests bridge the gap between source code and container images, providing an audit trail. Each image can be traced back to the exact commit it was built from, aiding compliance and debugging.
</br>
![Precise commit SHA](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/image-sha-digest.png)

Tags can be overwritten, leading to potential confusion if not managed carefully.</br>

**Tag Best Practices**
Avoid Using the latest Tag for Production
The latest tag can be ambiguous and may lead to deploying unintended versions.

**Define a Tagging Strategy**
Document and Enforce
Maintain Consistent Tagging Conventions

**Use Semantic Versioning**
Helps Track Scope of Changes
Major.Minor.Patch
Use tags like 1.0.0, 1.0.1, 2.0.0-beta to clearly indicate version numbers.

**Consider using image Digests**
Incorporate commit hashes for better traceability.
Use the commit SHA for tagging images in your CI workflow
**Use Additional Tags as Required**
Images Can Have 1 to n Tags


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

### Best Practices:
The importance of consistent practices to manage Docker images effectively, ensuring clarity and reliability in containerized workflows




**Using Labels:**

Labels provide metadata for images, offering a more reliable way to identify image details.  Consider the firms Proper image labels and metadata applied.  

The LABEL instruction in Dockerfiles allows adding key-value pairs, which can be inspected using docker inspect.
Labels are embedded in the image and cannot be overwritten like tags.

**Best Practices:**
Use labels in your Dockerfiles to add metadata about the image, such as version, maintainer, and source repository.

Use standardized labels, such as those recommended by the Open Container Initiative (OCI), like org.opencontainers.image.version for versioning or org.opencontainers.image.authors for author details.

Custom labels can be created using namespaces (e.g., com.myorg.myteam) for team-specific metadata.

**Final Thoughts:**

Proper use of tags and labels helps reduce confusion, ensures better documentation, and prevents production issues caused by image sprawl.

Refer to the official Docker documentation for detailed guidance:

### Resources:
[Docker Best Practices: Using Tags and Labels to Manage Docker Image Sprawl](https://www.docker.com/blog/docker-best-practices-using-tags-and-labels-to-manage-docker-image-sprawl/)
[Build, tag, and publish an image](https://docs.docker.com/get-started/docker-concepts/building-images/build-tag-and-publish-an-image/)
[Docker Tagging Best Practices] (https://www.docker.com/blog/docker-best-practices-using-tags-and-labels-to-manage-docker-image-sprawl/)