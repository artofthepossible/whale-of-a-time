### Using Docker Desktop and the Docker Init Command Lab

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
**Access the Application**</br>
Your application will be available at http://localhost:8080

### Part 3: Discover Docker Desktop
Here's a guided tour of Docker Desktop based on the provided knowledge sources:</br>

Docker Desktop is an all-in-one application for Mac, Linux, or Windows that allows you to build, share, and run containerized applications and microservices. </br>
It provides a user-friendly graphical interface for managing containers, applications, and images directly from your machine.</br>

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
