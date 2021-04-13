package dgsw.dbook.api.Response;

import java.util.Map;

public class LoginResponse extends ObjectResponse {

    String token;

    public LoginResponse(int code, String message) {
        super(code, message);
    }

    public LoginResponse(int code, String message, String token, Map<String, Object> object) {
        super(code, message, object);

        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
