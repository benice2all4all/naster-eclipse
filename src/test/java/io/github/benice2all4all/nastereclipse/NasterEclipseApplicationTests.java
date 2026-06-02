package io.github.benice2all4all.nastereclipse;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.security.oauth2.client.registration.discord.client-id=test-client-id",
        "spring.security.oauth2.client.registration.discord.client-secret=test-client-secret"
})
@AutoConfigureMockMvc
class NasterEclipseApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homePageIncludesDiscordLoginLink() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("/oauth2/authorization/discord")))
                .andExpect(content().string(containsString("Sign in with Discord")));
    }

    @Test
    void profileEndpointRedirectsAnonymousUsersToDiscordLogin() throws Exception {
        mockMvc.perform(get("/me"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/discord"));
    }

    @Test
    void profileEndpointReturnsDiscordUserDetailsForAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/me")
                        .with(oauth2Login().attributes(attributes -> {
                            attributes.put("id", "123456789");
                            attributes.put("username", "benice2all4all");
                            attributes.put("email", "ben@example.com");
                        })))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123456789"))
                .andExpect(jsonPath("$.username").value("benice2all4all"))
                .andExpect(jsonPath("$.email").value("ben@example.com"));
    }
}
