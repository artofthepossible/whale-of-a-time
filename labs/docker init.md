### Introduction to Docker Init Command Lab

Overview
In this lab, you'll learn the fundamentals docker init CLI commands to kickstart your containerization process, understand the inputs and outputs.

Time to Complete: 15-20 minutes

### How to Use This Hands-On Lab
1.  You have installed the latest version of Docker Desktop.
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


2. 
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
What port does your server listen on? 
```

### Part 2: Start your application

```sh
$ docker compose up -build
```
Your application will be available at http://localhost:8080

### Part 3: Stop your application 

```sh
$ docker compose down
```

### Resources
