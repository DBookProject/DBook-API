package dgsw.dbook.api.Response;

import java.util.Map;

public class LoginResponse extends ObjectResponse {

    String token;

    public LoginResponse(int status, String message) {
        super(status, message);
    }

    public LoginResponse(int status, String message, String token, Map<String, Object> object) {
        super(status, message, object);

        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
