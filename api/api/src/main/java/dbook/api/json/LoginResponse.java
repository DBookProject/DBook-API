package dbook.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    String message;
    String email;
    String token;

    //로그인 실패
    public LoginResponse(String message) {
        this.message = message;
    }

    //로그인 성공
    public LoginResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

}
