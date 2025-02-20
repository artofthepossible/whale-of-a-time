### Containerization Fundamentals


Overview
In this lab, you'll learn</br>

This lab covers Containerization Fundamentals:</br>
Introduction to containerization and Docker basics.</br>
Understanding Docker containers, their architecture, and key concepts.</br>
Learning to containerize applications, including Java and microservices.</br>
Guided tour of Discover Docker Desktop and its suite of products and the develop </br>

Docker Desktop is an all-in-one application for Mac, Linux, or Windows that allows you to build, share, and run containerized applications and microservices. </br>
It provides a user-friendly graphical interface for managing containers, applications, and images directly from your machine.</br>
 
## Development Benefits
![Developer Flow](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/docker-development-benefits.png)

When you open Docker Desktop, you'll see the Docker Desktop Dashboard, which offers several key views:</br>

**Containers view**
```sh
This provides a runtime view of all your containers and applications.
You can interact with containers, manage their lifecycle, and perform common actions.
```

![Containers View](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/containers-view.png)

**Images view**: 
```sh
Here you can see a list of your Docker images, run images as containers, pull the latest versions from Docker Hub, and inspect images.
It also shows image vulnerabilities and clean-up options.
```
![Images Viewer](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/images-view.png)


**Volumes view**: 
```sh
This displays a list of volumes and allows you to create, delete, and see which ones are being used.
```
![Volume Viewer](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/volume-view.png)

**Builds view**: 
```sh
You can inspect your build history and manage builders here.
```
![Builds View](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/builds-view.png)

The Dashboard also provides access to:</br>

**Settings menu** 
```sh
For configuring Docker Desktop
```
![Settings](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/settings.png)

**Troubleshoot menu**
```sh
For debugging and performing restart operations
```
![Troubleshoot](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/troubleshoot.png)

**Pro Tips Commands**
```sh
$ docker desktop start
✓ Starting Docker Desktop

$ docker desktop stop
✓ Stopping Docker Desktop

$ docker desktop restart
✓ Starting Docker Desktop

$ docker desktop status
✓ Check status

```

**Notifications center** 
```sh
For updates and other information
```


**Learning center** 
```sh
Quick in-app walkthroughs and resources
```

**Quick Search**
```sh
A key feature of Docker Desktop that is located in the Dashboard header.
This allows you to search for containers, images, extensions, volumes, and even Docker documentation.
```

### Docker scout and Docker Build Cloud Commands

**docker scout quickview** ✓ CVE overview and recommendations</br>
**docker scout cves** ✓ Analyze image for security vulnerabilities</br>
**docker scout policy** ✓ Evaluate image policies for compliance</br>
**docker buildx build** --builder dbc-builder image:tag</br>

### Containerization Fundamentals

**Introduction and Docker Basics**</br>
**What is Containerization?**</br>
Containerization is a technology that allows you to package an application and all its dependencies into a standardized unit called a container. This container can run consistently across different computing environments, ensuring that the application works the same way regardless of where it's deployed.</br>

**Why Use Containers?**</br>
**Containers offer several advantages:**</br>
**Isolation:** Each container runs in its own isolated environment, minimizing conflicts with other applications or system components.</br>

**Portability:** Containers can run on any system that supports the container runtime, making it easy to move applications between different environments.</br>

**Efficiency:** Containers share the host system's kernel, making them lighter and faster to start compared to traditional virtual machines.</br>

**Consistency:** Containers ensure that an application runs the same way in development, testing, and production environments.</br>

**Introduction to Docker**
Docker Desktop is a one-click-install application for your Mac, Linux, or Windows environment that lets you build, share, and run containerized applications and microservices.

It provides a straightforward GUI (Graphical User Interface) that lets you manage your containers, applications, and images directly from your machine.

Docker Desktop reduces the time spent on complex setups so you can focus on writing code. It takes care of port mappings, file system concerns, and other default settings, and is regularly updated with bug fixes and security updates.


**Key Docker Concepts**

**Container:** A runnable instance of an image that is isolated from other containers and the host system.</br>
**Image:** A read-only template containing instructions for creating a Docker container. Images includes everything needed to run an application.</br>
**Dockerfile:** A text file that contains instructions for building a Docker image.</br>
**Docker Hub:** A cloud-based registry service for sharing and managing container images.</br>

![Concepts](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/container-contepts.png)

**Basic Docker Commands**
**<p style="color:blue">docker run:</p>** Create and start a new container from an image</br>
**<p style="color:blue">docker build:</p>** Build a Docker image from a Dockerfile</br>
**<p style="color:blue">docker pull:</p>** Download an image from a registry (like Docker Hub)</br>
**<p style="color:blue">docker push:</p>** Upload an image to a registry</br>
**<p style="color:blue">docker ps:</p>** List running containers</br>

**Getting Started with Docker**
To begin using Docker:
Install Docker Desktop on your machine.
Run your first container using a simple command like:

```sh
docker run -d hello-world
```

Learn to build your own images and push them to Docker Hub.</br>
For a more detailed guide, refer to the Docker Get Started documentation.</br>
By understanding these fundamentals, you'll be well on your way to leveraging containerization and Docker in</br> your development workflow.</br>

![Developer Flow](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/container-contepts.png)

### Resources
[Docker Init](https://docs.docker.com/reference/cli/docker/init/)
[Containers View](https://docs.docker.com/desktop/use-desktop/container/)
[Images](https://docs.docker.com/desktop/use-desktop/images/)
[Volumes](https://docs.docker.com/desktop/use-desktop/volumes/)
[Builds](https://docs.docker.com/desktop/use-desktop/builds/)
