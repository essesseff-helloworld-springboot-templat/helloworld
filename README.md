# helloworld Application (Spring Boot)

Java Spring Boot implementation with trunk-based development and event-driven deployments via essesseff platform.

## Architecture

* **Branch Strategy**: Single `main` branch (trunk-based)
* **Auto-Deploy**: DEV only
* **Manual Deploy**: QA, STAGING, PROD (via essesseff)

## Development Workflow

```bash
# 1. Create feature branch
git checkout -b feature/my-feature

# 2. Make changes and commit
git commit -am "Add feature"

# 3. Push and create PR
git push origin feature/my-feature

# 4. After review, merge to main
# This triggers automatic build and deploy to DEV

# 5. Use essesseff UI for promotions:
#    - Developer declares Release Candidate
#    - QA accepts RC → deploys to QA (or alternatively rejects the promotion of the RC to QA)
#    - QA marks as Stable (or alternatively rejects the promotion to Stable)
#    - Release Engineer deploys from Stable Release to STAGING/PROD
```

## Local Development

### Prerequisites

* Java 17 or higher
* Maven 3.6+
* Docker (for containerization)

### Running Locally

```bash
# Using Maven
mvn spring-boot:run

# Or build and run the JAR
mvn clean package
java -jar target/helloworld-1.0.0.jar

# With custom port
PORT=8080 mvn spring-boot:run
```

### Development with Hot Reload

```bash
# Run with Spring Boot DevTools (included in pom.xml)
mvn spring-boot:run
```

## Docker

```bash
# Build container
docker build -t helloworld:local .

# Run container
docker run -p 8080:8080 helloworld:local

# Run with custom port
docker run -p 9090:9090 -e PORT=9090 helloworld:local
```

## Endpoints

* `/` - Main page with version information (HTML)
* `/health` - Health check (returns JSON)
* `/ready` - Readiness check (returns JSON)

### Example Responses

**GET /**
```html
<!DOCTYPE html>
<html>
<head>
    <title>Hello World - Spring Boot</title>
</head>
<body>
    <h1>Hello World Application (Spring Boot)</h1>
    <div class="info">
        <p><strong>Version:</strong> 1.0.0</p>
        <p><strong>Framework:</strong> Spring Boot</p>
        <p><strong>Language:</strong> Java</p>
    </div>
</body>
</html>
```

**GET /health**
```json
{
  "status": "healthy",
  "version": "1.0.0",
  "framework": "Spring Boot"
}
```

**GET /ready**
```json
{
  "status": "ready",
  "version": "1.0.0",
  "framework": "Spring Boot"
}
```

## Environment Variables

* `PORT` - Port to run the application on (default: 8080)
* `APP_VERSION` - Application version (default: 1.0.0)

## Project Structure

```
.
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/helloworld/
│       │       ├── HelloWorldApplication.java  # Main Spring Boot application
│       │       └── HelloWorldController.java   # REST controller
│       └── resources/
│           └── application.properties          # Configuration
├── pom.xml                                     # Maven dependencies
├── Dockerfile                                  # Container definition
├── semver.txt                                  # Version tracking
├── .gitignore                                  # Git ignore patterns
└── README.md                                   # This file
```

## Technology Stack

* **Framework**: Spring Boot 3.2.1
* **Language**: Java 17
* **Build Tool**: Maven 3.9
* **Container**: Docker with multi-stage build
* **Base Image**: Eclipse Temurin 17 JRE Alpine
* **Package**: com.example.helloworld

## Related Repositories

* Source: helloworld (this repo)
* Config DEV: helloworld-config-dev
* Config QA: helloworld-config-qa
* Config STAGING: helloworld-config-staging
* Config PROD: helloworld-config-prod
* Argo CD Config DEV: helloworld-argocd-dev
* Argo CD Config QA: helloworld-argocd-qa
* Argo CD Config STAGING: helloworld-argocd-staging
* Argo CD Config PROD: helloworld-argocd-prod

## Testing

```bash
# Run tests
mvn test

# Run tests with coverage
mvn test jacoco:report

# Test health endpoint
curl http://localhost:8080/health

# Test readiness endpoint
curl http://localhost:8080/ready

# Test main page
curl http://localhost:8080/
```

## Building

```bash
# Build JAR
mvn clean package

# Build without tests
mvn clean package -DskipTests

# Build Docker image
docker build -t helloworld:latest .
```

## Deployment

The application is deployed automatically to DEV environment after changes are merged to `main` branch and automatic code build succeeds. Promotion to QA, STAGING, and PROD environments is managed through the essesseff platform.

### Container Image Tags

Container images are tagged with the format:
```
{semver}-{git-hash}-{timestamp}
```

Example: `1.0.0-a1b2c3d-20231201T120000Z`

## CI/CD

GitHub Actions workflow (`.github/workflows/build.yml`) handles:
* Building the Docker image
* Pushing to GitHub Container Registry
* Generating build metadata
* Triggering essesseff deployment to DEV

## Health Checks

The application includes health check endpoints that can be used by:
* Kubernetes liveness/readiness probes
* Load balancers
* Monitoring systems

## Additional Notes

* The application runs as a non-root user in the container for security
* Multi-stage Docker build optimizes image size
* Health checks are included in the Dockerfile
* Spring Boot Actuator provides production-ready features
