package dgsw.dbook.api.Exception;

public class UserException extends RuntimeException {

    int code;
    String message;

    public UserException(int status, String message) {
        super(message);

        this.code = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
