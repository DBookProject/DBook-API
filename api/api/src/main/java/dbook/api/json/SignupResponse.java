package dbook.api.json;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupResponse {

    String message;

    public SignupResponse(String message) {
        this.message = message;
    }

}
