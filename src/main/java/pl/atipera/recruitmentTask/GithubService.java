package pl.atipera.recruitmentTask;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubService {
    public List<RepositoryResponse> getRepositories(String user){
        RepositoryResponse repo1 = new RepositoryResponse(
                "demo-repo",
                user,
                getBranches()
        );
        RepositoryResponse repo2 = new RepositoryResponse(
                "another-repo",
                user,
                getBranches()
        );

        return List.of(repo1, repo2);
    }
    private List<BranchResponse> getBranches(){
        return List.of(
                new BranchResponse("main", "abc123"),
                new BranchResponse("develop", "def456")
        );
    }
}
