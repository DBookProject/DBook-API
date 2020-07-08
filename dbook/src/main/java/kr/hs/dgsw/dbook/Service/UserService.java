package kr.hs.dgsw.dbook.Service;

import kr.hs.dgsw.dbook.Domain.EmailClass;
import kr.hs.dgsw.dbook.Domain.User;

public interface UserService {

    User signUp(User user);

    User login (User user);

    User userModified(User user);

    User getUser(String userid);

    User sendEmail(EmailClass emailInfo);

}