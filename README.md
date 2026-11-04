# CIA 2 — End-to-End Java CI/CD Pipeline

A Spring Boot Java web application scaffold for the CIA 2 DevOps assignment.

## Pipeline implemented in this starter

1. GitHub Actions triggers on pushes and pull requests to `main`.
2. Gitleaks scans the repository for committed secrets.
3. Maven compiles, tests, and packages the application.
4. On pushes to `main`, Docker builds a commit-tagged image and pushes it to GitHub Container Registry (GHCR).

The Kubernetes manifests and Ansible K3s installation playbook are starter files. Cloud deployment still needs to be configured and verified against your AWS account.

## Requirements

- JDK 21
- Maven 3.9+
- Docker
- Git
- A GitHub repository

## Run locally

```bash
mvn clean verify
mvn spring-boot:run
```

Open `http://localhost:8080/` and `http://localhost:8080/health`.

## Build and run with Docker

```bash
docker build -t cia2-java-app:local .
docker run --rm -p 8080:8080 -e APP_VERSION=local cia2-java-app:local
```

## GitHub setup

1. Create a GitHub repository.
2. Add these files and push the `main` branch.
3. Open **Actions** and inspect the workflow run.
4. After a successful push to `main`, find the image under the repository's **Packages** section.
5. Make the package public for a simple classroom demo, or configure Kubernetes image-pull credentials for a private package.

## Kubernetes

Before applying `k8s/deployment.yaml`, replace the image placeholder with the actual lowercase GHCR image name and a commit SHA that exists in GHCR. Set `APP_VERSION` to the same SHA. For a private package, configure an imagePullSecret in Kubernetes.

```bash
kubectl apply -f k8s/deployment.yaml
kubectl rollout status deployment/cia2-java-app
kubectl get pods,svc
```

The NodePort service uses port `30080`. On a cloud VM, only open that port in the EC2 security group to your own IP while testing. Do not expose SSH (port 22) to the entire internet.

## AWS cost warning

Amazon EKS has a separate hourly cluster charge plus worker-node and networking costs. This scaffold instead includes a K3s-on-EC2 option, which avoids the EKS control-plane fee but is not guaranteed to be free. Check your account's current Free Tier/credits and the AWS Pricing Calculator before creating resources. Stop/delete resources when finished.

## Evidence checklist

- [ ] GitHub repository and commit history
- [ ] Successful Gitleaks job
- [ ] Maven test results
- [ ] GHCR image tagged with the commit SHA
- [ ] AWS EC2 instance and K3s node ready
- [ ] Kubernetes pod Ready and Service/NodePort
- [ ] Browser response from `/`
- [ ] Second commit showing a changed `version` or message
- [ ] New workflow run, new image tag, and updated running response
- [ ] Final report with real screenshots and challenges/solutions

Never commit AWS keys, SSH private keys, GitHub tokens, or other secrets to Git.
