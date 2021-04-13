package dgsw.dbook.api.Response;

import java.util.List;

public class ListResponse extends Response {

    List<Object> objectList;

    public ListResponse(int code, String message) {
        super(code, message);
    }

    public ListResponse(int code, String message, List<Object> objectList) {
        super(code, message);

        this.objectList = objectList;
    }

    public List<Object> getObject() {
        return objectList;
    }

}
