package pl.atipera.recruitmentTask;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubController {
    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    private final GithubService githubService;

    @GetMapping("/users/{user}/repositories")
    List<RepositoryResponse> getRepositories(@PathVariable String user){
        return githubService.getRepositoriesWithBranches(user);
    }
}
