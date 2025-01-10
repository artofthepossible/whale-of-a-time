### Introduction to Docker Init Command Lab

Overview
In this lab, you'll learn the fundamentals docker init CLI commands to kickstart your containerization process, understand the inputs and outputs.

Time to Complete: 5-10 minutes

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

### Part 2: Start your application

```sh
$ docker compose up --build
```
In your terminal you can use the following commands to bring up Docker Desktop</br>
```sh
$ v View in Docker Desktop
```
Access the Application:</br>
Your application will be available at http://localhost:8080

### Part 3: Discover Docker Desktop
Here's a guided tour of Docker Desktop based on the provided knowledge sources:</br>

Docker Desktop is an all-in-one application for Mac, Linux, or Windows that allows you to build, share, and run containerized applications and microservices. </br>
It provides a user-friendly graphical interface for managing containers, applications, and images directly from your machine.</br>

When you open Docker Desktop, you'll see the Docker Desktop Dashboard, which offers several key views:</br>

**Containers view**: This provides a runtime view of all your containers and applications. </br>
You can interact with containers, manage their lifecycle, and perform common actions. </br>

**Images view**: Here you can see a list of your Docker images, run images as containers, pull the latest versions from Docker Hub, and inspect images. </br>
It also shows image vulnerabilities and clean-up options. </br>

**Volumes view**: This displays a list of volumes and allows you to create, delete, and see which ones are being used. </br>

**Builds view**: You can inspect your build history and manage builders here. </br>

The Dashboard also provides access to:</br>

**Settings menu** for configuring Docker Desktop</br>
Troubleshoot menu for debugging and performing restart operations</br>

**Notifications center** for updates and other information</br>
**Learning center** with quick in-app walkthroughs and resources</br>

**Quick Search** is A key feature of Docker Desktop that is located in the Dashboard header. </br>
This allows you to search for containers, images, extensions, volumes, and even Docker documentation. </br>

### Part 4: Stop your application 

```sh
$ docker compose down
```

### Resources
[Docker Init](https://docs.docker.com/reference/cli/docker/init/)
[Containers View](https://docs.docker.com/desktop/use-desktop/container/)
[Images](https://docs.docker.com/desktop/use-desktop/images/)
[Volumes](https://docs.docker.com/desktop/use-desktop/volumes/)
[Builds](https://docs.docker.com/desktop/use-desktop/builds/)
