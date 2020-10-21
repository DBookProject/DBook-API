package dbook.api.json;

import dbook.api.Domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginResponse extends Response {

    String token;
    String userName;
    String email;

    String profileImage;
    String libraryImage;
    String libraryName;

    public LoginResponse(int status, String message) {
        super(status, message);
    }

    public LoginResponse(int status, String message, String token, User user) {
        super(status, message);

        this.token = token;
        this.userName = user.getName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.libraryImage = user.getLibraryImage();
        this.libraryName = user.getLibraryName();
    }

    public String getToken() {
        return this.token;
    }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getProfileImage() { return profileImage; }
    public String getLibraryImage() { return libraryImage; }
    public String getLibraryName() { return libraryName; }

}
