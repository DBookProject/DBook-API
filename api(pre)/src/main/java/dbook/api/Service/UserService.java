package dbook.api.Service;

import dbook.api.Domain.User;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;

public interface UserService {

    Response sendEmail(User user);

    LoginResponse login(User user);

    User findUser(String email);

    Response certify(String code);

}