package dbook.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginResponse extends Response {

    String token;
    String email;

    public LoginResponse(int status, String message) {
        super(status, message);
    }

    public LoginResponse(int status, String message, String token, String email) {
        super(status, message);
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return this.token;
    }

    public String getEmail() {
        return this.email;
    }

}
