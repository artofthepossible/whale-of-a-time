### Debugging and Profiling Containerized Applications
Overview
In this lab, you'll learn how to use Docker tools to debug containerized applications, analyze logs, and profile CPU and memory characteristics.

Time to Complete: 15-20 minutes

### How to Use This Hands-On Lab
To get started with the lab, confirm the following
1. Install Docker Extensions:
    Go to the Docker Hub extensions page:
        Logs Explorer Extension
        Disk Usage Extension
        Volumes Backup Extension
    In Docker Desktop, navigate to the Extensions section and install the Logs Explorer, Disk Usage, and Volumes Backup extensions for enhanced logging capabilities.
2. Verify Installation:
    Once installed, you should be able to access the extensions through Docker Desktop, allowing you to view and manage logs, monitor disk usage, and back up volumes directly within the UI.
3. We will use react-java-mysql app for this lab
```sh
    git clone https://github.com/docker/awesome-compose
    cd react-java-mysql
```

### Using Docker Logs to View Container Logs
# Analyze Logs with Logs Explorer Extension
1. Open Logs Explorer:
From Docker Desktop, open the Logs Explorer extension and select the container you want to monitor.</br>
This provides an intuitive UI to browse logs and apply filters based on timestamps, log levels, or other criteria.</br>

View all your container logs in one place so you can debug and troubleshoot faster.</br>
Select the containers below to explore the logs</br>
```sh
react-java-mysql
```

2. Using the search bar, you can begin searching simply by typing 'warn
```sh
warn
```

3. Navigate to the error logs to find log 'Access denied for user'
```sh
Access denied for user
```

# Analyze Logs with CLI

4. With CLI, Identify the container id by Listing containers
```sh
docker container ls
```

5. For react-java-mysql-backend, you can use the cli to view the docker logs for the react-java-mysql-backend running container.
```sh
docker logs contaner-id
docker logs 033c0648323a
```
This command will show standard output and error logs. You can use additional flags:
         -f to follow the logs in real-time.
         --tail to limit the number of log lines shown.
         --since and --until to filter logs by timestamp.
![Logs Explorer](./labs/images/logsExplorer.png)

### Using Docker Debug to Profile Application
# Start Debugging a Container:
1. Use the docker debug command to attach a debugging session to a container.
```sh
docker debug container_id
docker debug 033c0648323a
```
2. exit

# Inspect CPU and Memory Usage:
3. To inspect the CPU and memory characteristics of a container, use the docker stats command, which provides a live stream of resource usage.
```sh
docker stats container_id
docker stats 033c0648323a
```
This allows you to explore the container’s state and identify issues such as CPU and memory usage.

# Inspect a Container’s Metadata:
Use docker inspect to retrieve detailed metadata of a container, including resource constraints and performance metrics.
```sh
docker inspect container_id
docker inspect 033c0648323a
```
This can help you understand the container's setup, including environment variables, resource limits, and other configurations.

# Review Application’s Disk Usage with Disk Usage Extension
1. View Disk Usage:
    Open the Disk Usage extension from Docker Desktop to analyze the disk space used by containers, volumes, and images.
    ![Disk Usage](./labs/images/diskUsage.png)

    This extension provides a breakdown of where storage is consumed, helping you identify and manage excessive disk usage.