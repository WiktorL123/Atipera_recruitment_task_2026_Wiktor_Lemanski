package pl.atipera.recruitmentTask;

public class GithubBranch {


    public GithubBranch(String name, GitHubCommit commit) {
        this.name = name;
        this.commit = commit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GitHubCommit getCommit() {
        return commit;
    }

    public void setCommit(GitHubCommit commit) {
        this.commit = commit;
    }

    private String name;
   private GitHubCommit commit;
}
