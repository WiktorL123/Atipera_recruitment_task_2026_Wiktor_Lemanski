package pl.atipera.recruitmentTask;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.ServerResponse.notFound;

@SpringBootTest
@AutoConnfigureMockMvc
@AutoConfigureWireMock(port = 8080)
class GithubControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnRepositoriesWithBranches_whenUserExists() throws Exception {

        stubFor(WireMock.get(urlEqualTo("/users/testUser/repos"))
                .willReturn(WireMock.okJson("""
                    [
                      {
                        "name": "repo-one",
                        "fork": false,
                        "owner": { "login": "testUser" }
                      },
                      {
                        "name": "forked-repo",
                        "fork": true,
                        "owner": { "login": "testUser" }
                      }
                    ]
                """)));

        stubFor(WireMock.get(urlEqualTo("/repos/testUser/repo-one/branches"))
                .willReturn(WireMock.okJson("""
                    [
                      {
                        "name": "main",
                        "commit": { "sha": "abc123" }
                      }
                    ]
                """)));

        mockMvc.perform(get("/users/testUser/repositories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].repositoryName").value("repo-one"))
                .andExpect(jsonPath("$[0].ownerLogin").value("testUser"))
                .andExpect(jsonPath("$[0].branches[0].branchName").value("main"))
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").value("abc123"));
    }

    @Test
    void shouldReturn404_whenUserDoesNotExist() throws Exception {
        stubFor(get(urlEqualTo("/users/unknown/repos"))
                .willReturn(notFound()));

        mockMvc.perform(get("/users/unknown/repositories"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}