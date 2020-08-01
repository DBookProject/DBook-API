package dbook.api.Service;

import dbook.api.Domain.Auth;
import dbook.api.Domain.User;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;
import dbook.api.json.SignupResponse;

public interface UserService {

    Response sendEmail(User user);

    SignupResponse signup(Auth auth);

    LoginResponse login(User user);

    Response shared(User user);

    Auth findAuth(String email);

    User findUser(String email);

}