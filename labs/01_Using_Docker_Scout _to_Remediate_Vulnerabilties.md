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
b. Setup docker desktop settings: Enable image SBOM indexing, Enable background SBOM indexing, Enable containerd for pulling and storing images then apply and restart. The containerd image store extends the range of image types that Docker Engine can natively interact with. This unlocks various new use cases, including: Building multi-platform images and images with attestations

### Part 1: Enable Docker Scout
1. Activate Docker Scout on atleast one repository
   ```sh 
   https://scout.docker.com/org/demonstrationorg/settings/repos
   ```
   or via cli 

   ```sh 
   docker scout repo enable --org ORG_NAME ORG_NAME/scout-demo
   
   docker scout repo enable --org demonstrationorg demonstrationorg/whale-of-a-time-server:latest 
   
   ```
You can also Manage repositories in the Docker Scout Dashboard → https://scout.docker.com/org/demonstrationorg/settings/repos

### Part 2: Scout: Docker Desktop and CLI - Issue identification and Analysis

Getting started with docker scout is significantly easier
Its optimized scout for first time users who may not be security experts and lights up immediately as you point your images

In our previous lab, we built our image which has some vulnerabilties. We can use the GUI or CLI to explore remediations and how we align to the firms policy guardrails

We can use Docker Desktop to learn more about our applciation
To Understand your application's dependencies, analyze the vulnerabilities, and act quickly with suggested remediation options. 

3. To view the same output via the cli
To view the summary of image vulnerabilities and recommendations, we run the first command listed here:
    ```sh 
    docker scout quickview demonstrationorg/whale-of-a-time-server:v1.0
    docker scout quickview whale-of-a-time-server:latest
    ```

4. To  View vulnerabilities, we run the second command docker scout cves
    ```sh 
    docker scout cves demonstrationorg/whale-of-a-time-server:v1.0
    docker scout cves whale-of-a-time-server:latest
    ```

5. To view the docker scout recommendations,we run the third command to compare and contrast the image across two builds,  
    ```sh 
    docker scout recommendations demonstrationorg/whale-of-a-time-server:v1.0
    docker scout recommendations whale-of-a-time-server:latest
    ```

## How would you resolve the "Supply chain attestations" policy evaluation results
Images should have both a software bill of materials and a provenance attached.</br>
| Requirement                                             | State                                       | Link                                                                                        |
|----------------------------------------------------|---------------------------------------------------|---------------------------------------------------------------------------------------------|
| SBOM attestation exists                           | Missing              | [Open](https://docs.docker.com/build/metadata/attestations/sbom/) |
| Provenance attestation with max mode exists                           | Missing              | [Open](https://docs.docker.com/build/metadata/attestations/slsa-provenance/) |

7. To build a Docker image without using the cache and include SBOM and provenance attestations, you can use the --no-cache option with the docker buildx build command.

**--no-cache**: This option ensures that the build process does not use any cached layers, forcing a fresh build of all layers.</br>
**--push**: This option pushes the built image directly to a Docker registry.</br>
**--sbom=true**: This option generates a Software Bill of Materials (SBOM) attestation for the image.</br>
**--provenance=true**: This option generates a provenance attestation for the image, providing metadata about how the image was built.</br>
**-t whale-of-a-time-server**: This tags the image with the specified name.</br>

Here's how you can do it:</br>
**Tag the Image then Push the Image**</br>

 
```sh
docker tag whale-of-a-time-server:latest demonstrationorg/whale-of-a-time:v1.0
```

```sh
docker buildx build --no-cache --push --sbom=true --provenance=true -t demonstrationorg/whale-of-a-time:v1.0 .
```
8. In lab 3 - Base Image Selection , we will explore how making a change to our base images can help use further improve our applications.  </br>


### Part 3: Health Scores - Docker Desktop:</br>

1. To view the health score of an image in Docker Desktop:</br>

    a. Open Docker Desktop and sign in to your Docker Enterprise account. </br>
    b. Navigate to the Images view and select the Hub tab.</br>
    c. In the list of repositories, the Health column displays the scores of the different tags that have been pushed to Docker Hub.</br>

    The health score badge is color-coded to indicate the overall health of the repository: </br>

```sh
    Green: A score of A or B.</br>
    Yellow: A score of C.</br>
    Orange: A score of D.</br>
    Red: A score of E or F.</br>
    Gray: An N/A score.</br>
```

![HealthScores on Docker Desktop](https://github.com/artofthepossible/whale-of-a-time/blob/main/labs/images/healthscores_dd.png)

### Part 4: Health Scores - Docker Hub:
1. To view the health score of an image in Docker Hub: </br>

2. Go to Docker Hub and sign in with your Docker Enterprise account. </br> or From Docker Desktop>Images> Hub Repositories>View in Hub
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


### Overview
Docker Scout is a comprehensive tool designed to enhance the security of containerized applications by providing detailed analysis and insights into container images. It operates as a standalone service and integrates seamlessly with Docker Desktop, Docker Hub, the Docker CLI, and the Docker Scout Dashboard. Docker Scout's primary function is to proactively secure the software supply chain by analyzing container images and compiling a Software Bill of Materials (SBOM). This SBOM is then matched against a continuously updated vulnerability database to identify and address security weaknesses.

**Key Features of Docker Scout:**
**Vulnerability Detection and Management**: Docker Scout performs automated vulnerability scans on container images, identifying known vulnerabilities early in the development process. This allows developers to address issues before they reach production, significantly reducing the risk of security breaches.

**Software Bill of Materials (SBOM)**: Docker Scout generates an SBOM for each container image, providing a detailed inventory of all components and software packages. This is crucial for understanding the composition of images and ensuring compliance with security standards.

**Health Scores**: Docker Scout assigns an alphabetical health score (A to F) to container images based on their security and compliance status. This score helps developers quickly assess the security posture of their images and prioritize remediation efforts.

**Policy Management**: Developers can define custom security policies within Docker Scout to monitor compliance with organizational standards. This includes checking for outdated base images, ensuring non-root user specifications, and monitoring for critical vulnerabilities.

**Continuous Monitoring**: Docker Scout continuously monitors images for new vulnerabilities, ensuring that they remain compliant with evolving security standards. This ongoing assessment helps maintain a secure software supply chain.

**Integration with Development Tools**: Docker Scout integrates with popular development tools like GitHub Actions and CI/CD pipelines, providing seamless security management within existing workflows. This integration allows for early detection and remediation of vulnerabilities, keeping developers in flow and reducing the burden on security teams.

Value for Developers:</br>
**Proactive Security**: By shifting security checks to the left, Docker Scout allows developers to identify and fix vulnerabilities early in the development cycle, reducing the cost and complexity of addressing issues later in production.

**Enhanced Compliance**: Docker Scout simplifies compliance with security standards and regulatory requirements by providing detailed insights and automated checks, ensuring that container images meet organizational and industry standards.

**Improved Efficiency**: With immediate feedback on security issues and policy violations, developers can maintain their workflow without significant disruptions, accelerating the development process and reducing time-to-market.

**Comprehensive Insights**: The detailed analysis provided by Docker Scout, including SBOMs and health scores, empowers developers to make informed decisions about the security and compliance of their container images.

Overall, Docker Scout is a valuable tool for developers seeking to enhance the security and compliance of their containerized applications, streamline their workflows, and reduce the risk of vulnerabilities reaching production. For more detailed information, you can refer to the Docker Scout documentation⁠.