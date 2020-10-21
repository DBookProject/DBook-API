package dgsw.dbook.api.Controller;

import dgsw.dbook.api.Domain.User;
import dgsw.dbook.api.Response.Response;
import dgsw.dbook.api.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        System.out.println("login - " + user.getEmail());
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Response> register(@RequestBody User user) {
        System.out.println("signUp - " + user.getEmail());
        return new ResponseEntity<>(userService.signUp(user), HttpStatus.OK);
    }

}