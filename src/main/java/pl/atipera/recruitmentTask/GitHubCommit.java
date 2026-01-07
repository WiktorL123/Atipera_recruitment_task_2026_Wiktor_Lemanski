package pl.atipera.recruitmentTask;

public class GitHubCommit {

    public GitHubCommit(String sha) {
        this.sha = sha;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    private String sha;
}
