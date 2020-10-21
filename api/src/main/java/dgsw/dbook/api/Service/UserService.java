package dgsw.dbook.api.Service;

import dgsw.dbook.api.Domain.User;
import dgsw.dbook.api.Response.LoginResponse;

public interface UserService {

    LoginResponse signUp(User user);

    LoginResponse login(User user);

    User findUser(String email);

}