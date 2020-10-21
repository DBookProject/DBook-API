package dgsw.dbook.api.Exception;

public class UserException extends RuntimeException {

    int status;
    String message;

    public UserException(int status, String message) {
        super(message);

        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
