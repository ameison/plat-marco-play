package auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {

    String token;

    public AuthToken(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
