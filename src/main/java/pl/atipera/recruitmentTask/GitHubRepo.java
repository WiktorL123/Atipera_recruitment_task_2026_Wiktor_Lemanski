package pl.atipera.recruitmentTask;

public class GitHubRepo {
    public GitHubRepo(String name, boolean fork, GithubOwner owner) {
        this.name = name;
        this.fork = fork;
        this.owner = owner;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public GithubOwner getOwner() {
        return owner;
    }

    public void setOwner(GithubOwner owner) {
        this.owner = owner;
    }

    private String name;
    private boolean fork;
    private GithubOwner owner;
}
