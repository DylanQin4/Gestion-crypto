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

    public String authenticate(String email, String password) {
        try {
            // Construire la requête pour l'authentification
            HttpPost post = new HttpPost("http://localhost:8000/api/login_check");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            // Créer le corps de la requête (json, par exemple)
            String json = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";
            post.setEntity(new StringEntity(json));

            // Envoyer la requête et obtenir la réponse
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);

                System.out.println("Request JSON: " + json);
                System.out.println("Response Status: " + response.getStatusLine());
                System.out.println("Response Body: " + responseString);
                // Traiter la réponse (par exemple, récupérer un token)
                if (response.getStatusLine().getStatusCode() == 200) {
                    // Exemple d'extraction d'un token ou d'un message
                    return responseString;
                } else {
                    // Gérer l'erreur
                    return "Erreur d'authentification: " + response.getStatusLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur: " + e.getMessage();
        }
    }

    public String register(String email, String nom, String password) {
        User newUser = null;
        try {
            // Construire la requête pour l'inscription
            HttpPost post = new HttpPost("http://localhost:8000/api/register");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            Role role = roleService.getRoleByName(Roles.USER.name());
            newUser = userService.save(new User(nom, email, Set.of(role)));

            // Créer le corps de la requête (json)
            String json = String.format(
                    "{\"email\":\"%s\", \"lastname\":\"%s\", \"firstname\":\"%s\", \"password\":\"%s\"}",
                    email, nom, nom, password
            );
            post.setEntity(new StringEntity(json));

            // Envoyer la requête et obtenir la réponse
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);

                System.out.println("Request JSON: " + json);
                System.out.println("Response Status: " + response.getStatusLine());
                System.out.println("Response Body: " + responseString);

                // Traiter la réponse
                if (response.getStatusLine().getStatusCode() == 201) { // 201 Created
                    return responseString; // Succès
                } else {
                    // Extraire le message d'erreur à partir du JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> responseMap = objectMapper.readValue(responseString, Map.class);
                    Object message = responseMap.get("message");

                    // Vérifier si le message contient des erreurs spécifiques
                    if (responseMap.containsKey("message")) {
                        Map<String, String> messageMap = (Map<String, String>) responseMap.get("message");
                        String emailError = messageMap.get("email"); // Obtenir le message pour l'email
                        throw new Exception("Erreur d'enregistrement: " + emailError);
                    }

                    throw new Exception("Erreur inattendue: " + responseString);
                }
            }
        } catch (Exception e) {
            if (newUser != null) {
                userService.delete(newUser);
            }
            return e.getMessage();
        }
    }

    public String validateEmail(String token) {
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

                if (response.getStatusLine().getStatusCode() == 200) {
                    // Retourner un message de succès ou extraire les données du corps de la réponse
                    return "Validation réussie : " + responseString;
                } else {
                    // Gérer les erreurs
                    return "Erreur de validation : " + responseString;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur : " + e.getMessage();
        }
    }
}
