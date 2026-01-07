# nazwafirmy_recruitment_task_imie_nazwisko

Recruitment task – simple Spring Boot application acting as a proxy to GitHub API.

The application exposes a REST endpoint that returns all **non-fork GitHub repositories** of a given user together with their branches and last commit SHA.

---

## Tech stack

- Java 25
- Spring Boot 4.0.1
- Gradle (Kotlin DSL)
- Spring Web MVC
- Spring Rest Client

Only the dependencies required by the task were used.

---

## Application behaviour

### Happy path

For an existing GitHub user, the application:
1. Fetches all repositories of the user from GitHub API
2. Filters out forked repositories
3. For each remaining repository, fetches its branches
4. Returns:
    - repository name
    - owner login
    - branch name
    - last commit SHA

### Error handling

If the given GitHub user does not exist, the application returns **404** in the following format:

```json
{
  "status": 404,
  "message": "User not found"
}
