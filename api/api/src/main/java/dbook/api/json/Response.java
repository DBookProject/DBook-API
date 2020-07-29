package dbook.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {

    String message;
    Object object;

    public Response(String message) {
        this.message = message;
    }

    public Response(Object object) {
        this.object = object;
    }

}
