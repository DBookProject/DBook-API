package dbook.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {

    int status;
    String message;

    public Response(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
