package io.github.benice2all4all.nastereclipse;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = "text/html")
    public String home() {
        return """
                <!DOCTYPE html>
                <html lang=\"en\">
                <head>
                    <meta charset=\"UTF-8\" />
                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />
                    <title>Naster Eclipse</title>
                </head>
                <body>
                    <main>
                        <h1>Naster Eclipse</h1>
                        <p>Discord auth is wired up.</p>
                        <p><a href=\"/oauth2/authorization/discord\">Sign in with Discord</a></p>
                    </main>
                </body>
                </html>
                """;
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal OAuth2User user) {
        Map<String, Object> profile = new LinkedHashMap<>();
        profile.put("id", user.getAttribute("id"));
        profile.put("username", user.getAttribute("username"));
        profile.put("email", user.getAttribute("email"));
        return profile;
    }
}
