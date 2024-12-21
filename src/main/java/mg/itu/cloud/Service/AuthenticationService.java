package mg.itu.cloud.Service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private CloseableHttpClient httpClient;

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
}
