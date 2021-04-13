package dgsw.dbook.api.Response;

import java.util.Map;

public class ObjectResponse extends Response {

    Map<String, Object> object;

    public ObjectResponse(int code, String message) {
        super(code, message);
    }

    public ObjectResponse(int code, String message, Map<String, Object> object) {
        super(code, message);

        this.object = object;
    }

    public Map<String, Object> getObject() {
        return object;
    }

}
