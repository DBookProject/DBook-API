package dgsw.dbook.api.Response;

import java.util.List;

public class ListResponse extends Response {

    List<Object> objectList;

    public ListResponse(int status, String message) {
        super(status, message);
    }

    public ListResponse(int status, String message, List<Object> objectList) {
        super(status, message);

        this.objectList = objectList;
    }

    public List<Object> getObject() {
        return objectList;
    }

}
