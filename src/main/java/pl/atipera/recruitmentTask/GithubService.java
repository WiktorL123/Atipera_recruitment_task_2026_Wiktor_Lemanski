package pl.atipera.recruitmentTask;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubService {

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    private final GithubClient githubClient;

    private List<GitHubRepo> getNotForkRepositories(String user){
        return githubClient.getGithubRepositories(user)
                .stream().filter(repo->!repo.isFork()).toList();
    }

    public List<RepositoryResponse> getRepositoriesWithBranches(String user){
        List<GitHubRepo> notForkReps = getNotForkRepositories(user);
        List<RepositoryResponse> result = mapRepositories(notForkReps);
        result.forEach(this::getAndSetBranches);
        return result;
    }

    private List<RepositoryResponse> mapRepositories(List<GitHubRepo> repositories){
        return repositories.stream().map(
                r->new RepositoryResponse(
                        r.getName(),
                        r.getOwner().getLogin(),
                        new ArrayList<>()
                )
        ).toList();
    }

    private void getAndSetBranches(RepositoryResponse response){
        List<GithubBranch> branches = githubClient.getGithubBranches(response.getOwnerLogin(), response.getRepositoryName());
        List<BranchResponse> branchResponses = branches.
                stream().
                map(branch -> new BranchResponse(branch.getName(), branch.getCommit().getSha())).
                toList();
       response.setBranches(branchResponses);
    }
}
