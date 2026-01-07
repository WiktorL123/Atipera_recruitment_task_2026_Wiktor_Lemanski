# Atipera_recruitment_task_2026_Wiktor_Lemanski

Recruitment task – a simple Spring Boot application acting as a proxy to the GitHub REST API.

The application exposes a REST endpoint that returns all **non-fork GitHub repositories** of a given user together with their branches and the last commit SHA for each branch.

The solution was intentionally kept simple, without introducing additional architectural layers or unnecessary abstractions, in line with the task requirements.

---

## Tech stack

* Java 25
* Spring Boot 4.0.1
* Gradle (Kotlin DSL)
* Spring Web MVC
* Spring Rest Client

Only the dependencies required by the task were used.

---

# ⚠️ Build & Test Status (Important Note)

**The project strictly follows the required technology stack:**
* **Java 25**
* **Spring Boot 4.0.1**
* Dependency: `org.springframework.boot:spring-boot-starter-webmvc`

**Current Status:**
The application code and integration tests logic are implemented correctly according to the acceptance criteria. However, **the build and tests are currently failing due to repository limitations.**

**Reason:**
The required artifacts for **Spring Boot 4.0.1** (specifically `spring-boot-starter-test` and `spring-boot-starter-webmvc`) combined with **Java 25** are not fully resolvable in the standard public Maven Central repository at the time of implementation. This results in `Cannot resolve symbol` errors during the compilation of test classes, as the build tool cannot fetch the necessary test libraries for the specified versions.

**Steps taken:**
* Verified configuration against standard Spring Boot BOMs.
* Attempted to force dependency resolution for the requested versions.
* Decided to **keep the configuration exactly as requested in the task description** rather than downgrading to a stable version (e.g., Spring Boot 3.4.x / Java 21) to strictly adhere to the recruitment requirements.




---

## Application behaviour

### Happy path

For an existing GitHub user, the application performs the following steps:

1. Fetches all repositories of the given user from the GitHub API
2. Filters out forked repositories
3. For each remaining repository:

   * fetches its branches
   * extracts branch name and last commit SHA
4. Returns a simplified response containing:

   * repository name
   * owner login
   * branch name
   * last commit SHA

---

### Error handling

If the given GitHub user does not exist, the application returns **HTTP 404** with the following response body:

```json
{
  "status": 404,
  "message": "User not found"
}
```

The error response format is consistent and independent of the GitHub API response structure.

---

## REST endpoint

### GET `/users/{username}/repositories`

#### Example request

```bash
GET /users/octocat/repositories
```

#### Example response

```json
[
  {
    "repositoryName": "git-consortium",
    "ownerLogin": "octocat",
    "branches": [
      {
        "branchName": "master",
        "lastCommitSha": "b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e"
      }
    ]
  },
  {
    "repositoryName": "hello-world",
    "ownerLogin": "octocat",
    "branches": [
      {
        "branchName": "master",
        "lastCommitSha": "7e068727fdb347b685b658d2981f8c85f7bf0585"
      }
    ]
  }
]
```

---

## Configuration

The application uses the GitHub REST API.

The following configuration properties are required:

```properties
github.base.url=https://api.github.com
github.api.token=YOUR_GITHUB_TOKEN
```

The GitHub token should be provided via environment variables or `application.properties`. It is required to avoid GitHub API rate limits.

---

## Running the application

To run the application locally:

```bash
./gradlew bootRun
```

The application will start on the default port **8080**.

---


```bash
./gradlew test
```

___
