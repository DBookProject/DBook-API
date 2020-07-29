package dbook.api.Service;

import dbook.api.Domain.User;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;
import dbook.api.json.SignUpResponse;

public interface UserService {

    SignUpResponse signup(User user);

    LoginResponse login(User user);

    Response shared(User user);

    User findUser(String  userid);

//    String sendEmail(String email);

}