package pl.atipera.recruitmentTask;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GithubClient {

    private final RestClient restClient;
    private String token;
    private String baseUrl;

    public GithubClient(RestClient.Builder builder,
                        @Value("${github.api.token}")  String token,
                        @Value("${github.base.url}") String baseUrl){
        this.token = token;
        this.baseUrl = baseUrl;
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public List<GitHubRepo> getGithubRepositories(String user) {
        try {
            return restClient.get()
                    .uri("/users/{user}/repos", user)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<GitHubRepo>>() {});
        } catch (HttpClientErrorException.NotFound e) {
            throw new RepositoryNotFoundException();
        }
    }


    public List<GithubBranch> getGithubBranches(String owner, String repoName) {
        try {
            return restClient.get()
                    .uri("/repos/{owner}/{repo}/branches", owner, repoName )
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<GithubBranch>>() {});
        }
        catch (HttpClientErrorException.NotFound e) {
            throw new RepositoryNotFoundException();
        }
    }
}
