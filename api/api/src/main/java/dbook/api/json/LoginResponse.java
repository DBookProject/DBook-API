package dbook.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginResponse extends Response {

    String token;

    public LoginResponse(int status, String message) {
        super(status, message);
    }

    public LoginResponse(int status, String message, String token) {
        super(status, message);
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}
