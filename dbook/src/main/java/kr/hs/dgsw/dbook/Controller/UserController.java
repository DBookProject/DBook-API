package kr.hs.dgsw.dbook.Controller;

import kr.hs.dgsw.dbook.Domain.EmailClass;
import kr.hs.dgsw.dbook.Domain.User;
import kr.hs.dgsw.dbook.Service.UserService;
import kr.hs.dgsw.dbook.json.ResponseLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userid}")
    public User Modify(@PathVariable String userid){
        return this.userService.getUser(userid);
    }

    @PostMapping("/user/signup")
    public User Signup(@RequestBody User user) {
        return this.userService.signUp(user);
    }

    @PostMapping("/user/login")
    public User Login(@RequestBody User user){
        return this.userService.login(user);
    }

    @PutMapping("/user/modify")
    public User Modify(@RequestBody User user){
        return this.userService.userModified(user);
    }

    @PostMapping("/user/emails")
    public User SendEmail(@RequestBody EmailClass emailInfo) { return this.userService.sendEmail(emailInfo); }

}