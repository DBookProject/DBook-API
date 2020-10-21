package dgsw.dbook.api.Response;

import java.util.Map;

public class ObjectResponse extends Response {

    Map<String, Object> object;

    public ObjectResponse(int status, String message) {
        super(status, message);
    }

    public ObjectResponse(int status, String message, Map<String, Object> object) {
        super(status, message);

        this.object = object;
    }

    public Map<String, Object> getObject() {
        return object;
    }

}
