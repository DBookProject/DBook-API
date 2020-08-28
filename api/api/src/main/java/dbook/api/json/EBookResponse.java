package dbook.api.json;

public class EBookResponse extends Response {

    String bookPath;

    public EBookResponse(int status, String message, String bookPath) {
        super(status, message);

        this.bookPath = bookPath;
    }

    public String getBookPath() {
        return bookPath;
    }

}
