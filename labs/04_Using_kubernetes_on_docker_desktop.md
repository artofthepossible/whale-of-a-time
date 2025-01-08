### Introduction to Deploying your Application Lab

Overview
In this lab, you'll learn the fundamentals of kubernetes on docker desktop.

Time to Complete: 30-45 minutes

### How to Use This Hands-On Lab
1. To get started with kubernetes on docker desktop, you need to completed the following:
Build your image
Push image to registry
Enable kubernetes single or multi-node cluster when starting Docker Desktop.

### Part 1: Start Kubernetes on Docker Desktop

1. Open Docker Desktop:
   - Launch Docker Desktop from your applications menu.

2. Enable Kubernetes:
   - Click on the gear icon (⚙️) in the top-right corner to open the settings.
   - In the settings menu, navigate to the "Kubernetes" tab.
   - Check the box labeled "Enable Kubernetes".
   - Click "Apply & Restart" to apply the changes and restart Docker Desktop.

3. Wait for Kubernetes to Start:
   - Docker Desktop will take a few minutes to start Kubernetes. You can monitor the progress in the Docker Desktop status bar.
   - Once Kubernetes is running, you will see a green light next to "Kubernetes" in the Docker Desktop settings.

4. Verify Kubernetes is Running:
   - Open a terminal and run the following command to check the status of Kubernetes:
     ```sh
     kubectl get nodes
     ```
   - You should see a list of nodes indicating that Kubernetes is up and running.

5. Setup the context for docker-desktop
     ```sh
      kubectl config use-context docker-desktop
     ```

6. Deploy an Application to Kubernetes:
   - You can now deploy your applications to the local Kubernetes cluster using `kubectl` commands or Kubernetes manifests.

### Part 2: Create Helm chart
1. Initialize a helm chart for the app

   ```sh
   helm create whale-of-a-time
   ```

This command will generate the following files:
   ```sh
   spring-boot-app/
├── .helmignore   # Contains patterns to ignore when packaging Helm charts.
├── Chart.yaml    # Information about your chart
├── values.yaml   # The default values for your templates
├── charts/       # Charts that this chart depends on
└── templates/    # The template files
    └── tests/    # The test files
   ```

1b. Update the chart.yaml to point to the target deployment artifcact/image 
   ```sh
- image:
  - repository: nginx
    pullPolicy: IfNotPresent
    # Overrides the image tag whose default is the chart appVersion.
  - tag: ""

+ image:
  + repository: demonstrationorg/whale-of-a-time-alpaquita-liberica-openjdk-alpine
    pullPolicy: IfNotPresent
    # Overrides the image tag whose default is the chart appVersion.
  + tag: "v1.0"
   ```

1b. Update the values.yaml to point to the target deployment artifcact/image 
   ```sh
service:
  -type: ClusterIP
  -port: 80

service:
  +type: NodePort
  +port: 8080

- imagePullSecrets: []

+ imagePullSecrets:
+    - name: regcred
   ```

2. Navigate to the Helm Chart Directory:
a. Change directory to the Helm chart location:

This command will package a chart directory into a chart archive

   ```sh
      cd whale-of-a-time/
      helm package .
   ```

### Part 3: Using KIND with Docker Desktop to Deploy app to local k8 cluster 
Steps to deploy your Spring Boot application to a local kind cluster using the provided Helm charts.

1. Using the helm chart to deploy the app to the local k8 cluster
Deploy the Application Using Helm

Example:
```sh
helm install whale-of-a-time .
```
This command installs a chart archive.

```sh 
NAME: whale-of-a-time
LAST DEPLOYED: Mon Jan  6 18:06:25 2025
NAMESPACE: default
STATUS: deployed
REVISION: 1
NOTES:
1. Get the application URL by running these commands:
  export NODE_PORT=$(kubectl get --namespace default -o jsonpath="{.spec.ports[0].nodePort}" services whale-of-a-time)
  export NODE_IP=$(kubectl get nodes --namespace default -o jsonpath="{.items[0].status.addresses[0].address}")
  echo http://$NODE_IP:$NODE_PORT
bash-3.2$ export NODE_PORT=$(kubectl get --namespace default -o jsonpath="{.spec.ports[0].nodePort}" services whale-of-a-time)
bash-3.2$ export NODE_IP=$(kubectl get nodes --namespace default -o jsonpath="{.items[0].status.addresses[0].address}")
bash-3.2$ echo http://$NODE_IP:$NODE_PORT

```

2. Access the Application:
Forward a local port to the service to access the application:
Expose the services via node port
```sh
kubectl port-forward svc/whale-of-a-time 8080:8080
```

3. Open your browser and navigate to http://localhost:8080 to access the Spring Boot application.


### Part 4: Cleanup
To uninstall the release

```sh
helm uninstall whale-of-a-time
```

5. Check the status of the pods:
To lists all pods running in a cluster, along with basic information like pod name, status, and age

```sh
kubectl get pods
```

6. Check the Services: Ensure that the pods are running.
To  List one or more services
```sh
kubectl get services
```

### Part 5: Extra Commands

1. Verify that the Helm release is undeployed successfully.
Check the Helm Release: Verify that the Helm release is deployed successfully.
```sh
helm list
```

2. Check the Logs: View the logs of the application pod to ensure it is running correctly.

```sh
kubectl logs <pod-name>
```


By following these steps, you should have used helm to deploy an application to a local kind cluster and be able to curl the endpoint.

### Resources
https://docs.docker.com/docker-hub/repos/manage/hub-images/oci-artifacts/
https://docs.docker.com/desktop/features/kubernetes/