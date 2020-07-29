package dbook.api.json;

import lombok.Data;

@Data
public class SignUpResponse {

    String message;
    Integer code;
    Object object;

    public SignUpResponse(){
        this.code = 0;
    }

    public SignUpResponse(String message) {
        this.message = message;
        this.code = 0;
    }

    public SignUpResponse(Object object) {
        this.object = object;
    }
}
