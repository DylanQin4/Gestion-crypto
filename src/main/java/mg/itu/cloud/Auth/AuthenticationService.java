package mg.itu.cloud.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import mg.itu.cloud.user.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AuthenticationService {
    private final CloseableHttpClient httpClient;
    private final UserService userService;
    private final RoleService roleService;

    public AuthenticationService(CloseableHttpClient httpClient, UserService userService, RoleService roleService) {
        this.httpClient = httpClient;
        this.userService = userService;
        this.roleService = roleService;
    }

    public AuthenticatorResponse authenticate(String email, String password) {
        try {
            // Construire la requête pour l'authentification
            HttpPost post = new HttpPost("http://localhost:8000/api/login_check");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            // Créer le corps de la requête JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);
            String json = objectMapper.writeValueAsString(requestBody);
            post.setEntity(new StringEntity(json));

            // Envoyer la requête et obtenir la réponse
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String responseString = EntityUtils.toString(response.getEntity());

                System.out.println("Request JSON: " + json);
                System.out.println("Response Status: " + response.getStatusLine());
                System.out.println("Response Body: " + responseString);

                // Désérialiser la réponse JSON
                AuthenticatorResponse authenticatorResponse = objectMapper.readValue(responseString, AuthenticatorResponse.class);

                return authenticatorResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AuthenticatorResponse("error", null, "Erreur : " + e.getMessage());
        }
    }

    public AuthenticatorResponse register(String email, String nom, String password) {
        User newUser = null;
        try {
            // Construire la requête pour l'inscription
            HttpPost post = new HttpPost("http://localhost:8000/api/register");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            // Créer l'utilisateur localement en base
            Role role = roleService.getRoleByName(Roles.USER.name());
            newUser = userService.save(new User(nom, email, Set.of(role)));

            // Créer le corps de la requête JSON
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("lastname", nom);
            requestBody.put("firstname", null);
            requestBody.put("password", password);
            String json = objectMapper.writeValueAsString(requestBody);

            post.setEntity(new StringEntity(json));

            // Envoyer la requête et obtenir la réponse
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String responseString = EntityUtils.toString(response.getEntity());

                System.out.println("Request JSON: " + json);
                System.out.println("Response Status: " + response.getStatusLine());
                System.out.println("Response Body: " + responseString);

                // Désérialiser la réponse JSON
                AuthenticatorResponse authenticatorResponse = objectMapper.readValue(responseString, AuthenticatorResponse.class);

                if (response.getStatusLine().getStatusCode() == 201) { // 201 Created
                    return authenticatorResponse; // Succès
                } else {
                    // Annuler la création de l'utilisateur localement en cas d'échec
                    if (newUser != null) {
                        userService.delete(newUser);
                    }
                    return authenticatorResponse; // Contient le message d'erreur
                }
            }
        } catch (Exception e) {
            if (newUser != null) {
                userService.delete(newUser);
            }
            return new AuthenticatorResponse("error", null, "Erreur : " + e.getMessage());
        }
    }

    public AuthenticatorResponse validateEmail(String token) {
        try {
            // Construire l'URL avec le token
            String url = "http://localhost:8000/api/validate-email/" + token;

            // Créer une requête GET
            HttpGet get = new HttpGet(url);
            get.setHeader("Accept", "application/json");

            // Exécuter la requête
            try (CloseableHttpResponse response = httpClient.execute(get)) {
                String responseString = EntityUtils.toString(response.getEntity());

                System.out.println("Request URL: " + url);
                System.out.println("Response Status: " + response.getStatusLine());
                System.out.println("Response Body: " + responseString);

                // Désérialiser la réponse JSON en un objet AuthenticatorResponse
                ObjectMapper objectMapper = new ObjectMapper();
                AuthenticatorResponse authenticatorResponse = objectMapper.readValue(responseString, AuthenticatorResponse.class);

                return authenticatorResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AuthenticatorResponse("error", null, "Erreur : " + e.getMessage());
        }
    }

    public AuthenticatorResponse validatePin(String pin) {
        try {
            // Construire l'URL avec le token
            String url = "http://localhost:8000/api/validate-pin/" + pin;

            // Créer une requête GET
            HttpGet get = new HttpGet(url);
            get.setHeader("Accept", "application/json");

            // Exécuter la requête
            try (CloseableHttpResponse response = httpClient.execute(get)) {
                String responseString = EntityUtils.toString(response.getEntity());

                System.out.println("Request URL: " + url);
                System.out.println("Response Status: " + response.getStatusLine());
                System.out.println("Response Body: " + responseString);

                // Désérialiser la réponse JSON en un objet PinResponse
                ObjectMapper objectMapper = new ObjectMapper();
                AuthenticatorResponse authenticatorResponse = objectMapper.readValue(responseString, AuthenticatorResponse.class);

                return authenticatorResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AuthenticatorResponse("error", null, "Erreur : " + e.getMessage());
        }
    }

}
