package dbook.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    String message;
    String userid;
    String token;

    //로그인 실패
    public LoginResponse(String message) {
        this.message = message;
    }

    //로그인 성공
    public LoginResponse(String userid, String token) {
        this.userid = userid;
        this.token = token;
    }

}
