### Using Docker Scout Lab to Detect and Remediate CVEs

Overview
In this lab, you'll learn the fundamentals of docker scout.
Docker Scout, 24/7 analysis of your images, and managing registries.

Detect and Remediate vulnerabilities earlier in development to Improve you Secure Software Supply Chain 

## Development Benefits
![Developer Flow](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/docker-scout-benefits.png)


Time to Complete: 15-20 minutes

### How to Use This Hands-On Lab
1. To get started with Docker Scout, you need to:
a. Activate Docker Scout on at least one repository

### Overview
Docker Scout enhances container security by providing automated vulnerability detection and remediation, addressing insecure container images, and ensuring compliance with security standards.
It Works well with Docker Desktop, GitHub Actions, Jenkins, Kubernetes, and other CI solutions.
[Software supply chain security] (https://youtu.be/YzNK6E7APv0)
[Software Bill Of Materials] (https://youtu.be/PbS4y7C7h4A)

Building a secure software supply chain involves several key steps, such as:

Identify the software components and dependencies you use to build and run your applications.
Automate security testing throughout the software development lifecycle.
Monitor your software supply chain for security threats.
Implement security policies that govern how software is built, and the components it contains.

### Part 1: Enable Docker Scout
1. Activate Docker Scout on atleast one repository
   ```sh 
   https://scout.docker.com/org/demonstrationorg/settings/repos
   ```
   or via cli 

   ```sh 
   docker scout repo enable --org ORG_NAME ORG_NAME/scout-demo
   
   docker scout repo enable --org demonstrationorg demonstrationorg/whale-of-a-time-server 
   
   ```
You can also Manage repositories in the Docker Scout Dashboard → https://scout.docker.com/org/demonstrationorg/settings/repos

2. Setup docker desktop settings
    Enable image SBOM indexing.
    Enable background SBOM indexing
    Enable containerd for pulling and storing images then apply and restart 
     The containerd image store extends the range of image types that Docker Engine can natively interact with. This unlocks various new use cases, including:
        Building multi-platform images and images with attestations

### Part 2: Scout: Docker Desktop and CLI - Issue identification and Analysis

Getting started with docker scout is significantly easier
Its optimized scout for first time users who may not be security experts and lights up immediately as you point your images

In our previous lab, we built our image which has some vulnerabilties.
Additionally, this image is non-compliant with some policies below
Missing supply chain attestation(s)

To Fix - Missing supply chain attestation(s), Rebuild the image with a new tag and push it to your Docker Hub repository:

    ```sh 
    docker build --push --sbom=true --provenance=true -t demonstrationorg/whale-of-a-time-server:v1.0 .
    ```

1. Activate Docker Scout on the repository with ths command 
  
   ```sh 
   docker scout repo enable --org ORG_NAME ORG_NAME/scout-demo
   docker scout repo enable --org demonstrationorg demonstrationorg/whale-of-a-time-server:v1.0
   ```

   The command is successful, if you see ✓ Enabled Docker Scout on ORG_NAME/IMAGE_NAME 
Alternatively, navigate to the following dashboard to Manage repositories in the Docker Scout Dashboard → https://scout.docker.com/org/demonstrationorg/settings/repos

We can use Docker Desktop to learn more about our applciation
To Understand your application's dependencies, analyze the vulnerabilities, and act quickly with suggested remediation options. 

3. To view the same output via the cli
To view the summary of image vulnerabilities and recommendations, we run the first command listed here:
    ```sh 
    docker scout quickview demonstrationorg/whale-of-a-time-server:v1.0
    ```

4. To  View vulnerabilities, we run the second command docker scout cves
    ```sh 
    docker scout cves demonstrationorg/whale-of-a-time-server:v1.0
    ```

5. To view the docker scout recommendations,we run the third command to compare and contrast the image across two builds,  
    ```sh 
    docker scout recommendations demonstrationorg/whale-of-a-time-server:v1.0
    ```

6. To  View policy violations, we run the third command
    ```sh 
   docker scout policy whale-of-a-time-server --org demonstrationorg
    ```

## How would you resolve the "Supply chain attestations" policy evaluation results
Images should have both a software bill of materials and a provenance attached.

                  Requirement                 │  State   
──────────────────────────────────────────────┼──────────
  SBOM attestation exists                     │ Missing  
  Provenance attestation with max mode exists │ Missing  
7. To build a Docker image without using the cache and include SBOM and provenance attestations, you can use the --no-cache option with the docker buildx build command.

**--no-cache**: This option ensures that the build process does not use any cached layers, forcing a fresh build of all layers.</br>
**--push**: This option pushes the built image directly to a Docker registry.</br>
**--sbom=true**: This option generates a Software Bill of Materials (SBOM) attestation for the image.</br>
**--provenance=true**: This option generates a provenance attestation for the image, providing metadata about how the image was built.</br>
**-t whale-of-a-time-server**: This tags the image with the specified name.</br>

Here's how you can do it:

docker buildx build --no-cache --push --sbom=true --provenance=true -t ORG/IMAGE:TAG .
    ```sh 
docker tag whale-of-a-time-server:latest demonstrationorg/whale-of-a-time:v1.0
docker buildx build --no-cache --push --sbom=true --provenance=true -t demonstrationorg/whale-of-a-time:v1.0 .
    ```

   
### Part 3: Health Scores - Docker Desktop:

1. To view the health score of an image in Docker Desktop:

    a. Open Docker Desktop and sign in to your Docker Enterprise account. </br>
    b. Navigate to the Images view and select the Hub tab.</br>
    c. In the list of repositories, the Health column displays the scores of the different tags that have been pushed to Docker Hub.</br>

    The health score badge is color-coded to indicate the overall health of the repository: </br>

    Green: A score of A or B.</br>
    Yellow: A score of C.</br>
    Orange: A score of D.</br>
    Red: A score of E or F.</br>
    Gray: An N/A score.</br>
![HealthScores on Docker Desktop](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/healthscores_dd.png)

### Part 4: Health Scores - Docker Hub:
1. To view the health score of an image in Docker Hub: </br>

2. Go to Docker Hub and sign in with your Docker Enterprise account. </br>
3. Navigate to your organization's page. </br>
4. In the list of repositories, you can see the health score of each repository based on the latest pushed tag. </br>
![HealthScores on Docker Hub](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/healthscores_dh.png)


### Part 6: Health Scores - Improving your health score
To improve the health score of an image, take steps to ensure that the image is compliant with the Docker Scout recommended policies. </br>

1. Go to the Docker Scout Dashboard. </br>
2. Sign in using your Docker ID Enterprise account. </br>
3. Go to Repository settings and enable Docker Scout for your Docker Hub image repositories. </br>
4. Analyze the policy compliance for your repositories, and take actions to ensure your images are policy-compliant. </br>
Since policies are weighted differently, prioritize the policies with the highest scores for a greater impact on your image's overall score. </br>

a. In Docker Desktop, select View in Hub
b. In Docker Hub, select [view in Docker Scout Dashboard](https://scout.docker.com/reports/org/demonstrationorg/images/host/hub.docker.com/repo/demonstrationorg%2Fwhale-of-a-time-scout-demo/tag/v3/digest/sha256%3A1ac649615092d9e30ff4d6c10ad84733d5212451e406b9a331143c1208f18ff4?utm_source=hub&utm_medium=actions-button&_gl=1*1kj2f6f*_gcl_aw*R0NMLjE3MzA3NjQyMDkuQ2owS0NRaUFfcUc1QmhEVEFSSXNBQTBVSFNLZ2NEcUJJenQ3a1JfaktfSmoyR1JBaC1heHd4QlJaQmpaMEc3TUlmand6STNPUktqVDhUUWFBbVdjRUFMd193Y0I.*_gcl_au*MTQxNjYwMDAzNC4xNzMzNzczMTAy*_ga*ODQyNDk2NzMyLjE3MDI0MDA3MjY.*_ga_XJWPQMJYHQ*MTczNjE4NjcxNy4xNTg1LjEuMTczNjE4OTQ5OC42MC4wLjA.)
c. Under [Policy Status](https://scout.docker.com/reports/org/demonstrationorg/images/host/hub.docker.com/repo/demonstrationorg%2Fwhale-of-a-time-scout-demo/tag/v3/digest/sha256%3A1ac649615092d9e30ff4d6c10ad84733d5212451e406b9a331143c1208f18ff4/policy)
![HealthScores on Docker Scout](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/healthscores_ds.png)
