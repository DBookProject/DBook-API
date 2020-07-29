package dbook.api.Controller;

import dbook.api.Domain.User;
import dbook.api.Service.UserService;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;
import dbook.api.json.SignUpResponse;
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

    @PostMapping("/users/signup")
    public ResponseEntity<SignUpResponse> SignUp(@RequestBody User user) {
        SignUpResponse signUpResponse = userService.signup(user);
        return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
    }

    @PostMapping("/users/shared")
    public ResponseEntity<Response> Shared(@RequestBody User user) {
        Response response = userService.shared(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}