package dgsw.dbook.api.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {

    int code;
    String message;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
