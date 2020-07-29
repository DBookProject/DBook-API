package kr.hs.dgsw.dbook.Service;

import kr.hs.dgsw.dbook.Domain.User;
import kr.hs.dgsw.dbook.json.LoginResponse;
import kr.hs.dgsw.dbook.json.SignUpResponse;

public interface UserService {

    SignUpResponse signup(User user);

    LoginResponse login(User user);

    User findUser(String  userid);

    String sendEmail(String email);

}