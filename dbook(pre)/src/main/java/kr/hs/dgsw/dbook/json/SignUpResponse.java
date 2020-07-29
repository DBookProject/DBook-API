package kr.hs.dgsw.dbook.json;

import lombok.Data;

@Data
public class SignUpResponse {

    String message;

    Integer code;

    public SignUpResponse(){
        this.code = 0;
    }

    public SignUpResponse(String message) {
        this.message = message;
        this.code = 0;
    }
}
