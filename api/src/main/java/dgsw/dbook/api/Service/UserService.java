package dgsw.dbook.api.Service;

import dgsw.dbook.api.Domain.User;
import dgsw.dbook.api.Domain.UserData;
import dgsw.dbook.api.Response.LoginResponse;
import org.springframework.core.io.Resource;

public interface UserService {

    LoginResponse signUp(UserData userData);

    LoginResponse login(User user);

    Resource getImage(long imageId);

    User findUser(String email);

}