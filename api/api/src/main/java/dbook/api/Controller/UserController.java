package dbook.api.Controller;

import dbook.api.Domain.Auth;
import dbook.api.Domain.User;
import dbook.api.Service.UserService;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;
import dbook.api.json.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/sendEmail")
    public ResponseEntity<Response> SendEmail(@RequestBody User user) {
        return new ResponseEntity<>(userService.sendEmail(user), HttpStatus.OK);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<User> GetUser(@PathVariable String email) {
        User user = userService.findUser(email);

        if(user.getEmail().equals("ok"))
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PostMapping("/users/logins")
    public ResponseEntity<LoginResponse> Login(@RequestBody User user) {
        LoginResponse loginResponse = userService.login(user);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/users/signup")
    public ResponseEntity<SignupResponse> SignUp(@RequestBody Auth auth) {
        return new ResponseEntity<>(userService.signup(auth), HttpStatus.OK);
    }

    @PostMapping("/users/shared")
    public ResponseEntity<Response> Shared(@RequestBody User user) {
        Response response = userService.shared(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}