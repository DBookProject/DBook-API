package dbook.api.Service;

import dbook.api.Domain.Auth;
import dbook.api.Domain.User;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;

public interface UserService {

    Response sendEmail(User user);

    Response signup(Auth auth);

    LoginResponse login(User user);

    Auth findAuth(String email);

    User findUser(String email);

}