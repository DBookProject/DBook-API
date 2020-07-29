package kr.hs.dgsw.dbook.Controller;

import kr.hs.dgsw.dbook.Domain.User;
import kr.hs.dgsw.dbook.Service.UserService;
import kr.hs.dgsw.dbook.json.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userid}")
    public ResponseEntity<User> GetUser(@PathVariable String userid) {
        User user = userService.findUser(userid);

        if(user.getUserId().equals("ok"))
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PostMapping("/users/logins")
    public ResponseEntity<LoginResponse> Login(@RequestBody User user) {
        LoginResponse loginResponse = userService.login(user);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}