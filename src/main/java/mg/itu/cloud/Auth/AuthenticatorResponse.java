package mg.itu.cloud.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticatorResponse(
        String status,
        @JsonProperty("access-token") String accessToken,
        String message
) {
}
