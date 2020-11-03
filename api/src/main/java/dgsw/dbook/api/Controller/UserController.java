package dgsw.dbook.api.Controller;

import dgsw.dbook.api.Domain.User;
import dgsw.dbook.api.Domain.UserData;
import dgsw.dbook.api.Response.Response;
import dgsw.dbook.api.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        log.info("login - {}", user);
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Response> register(@ModelAttribute UserData userData) {
        log.info("signUp - {}", userData);
        return new ResponseEntity<>(userService.signUp(userData), HttpStatus.OK);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable long imageId, HttpServletRequest request) {
        log.info("getUserProfileImage - {}", imageId);

        Resource resource = userService.getImage(imageId);

        if(resource == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } catch (Exception e) {
            log.error("getImage Error", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}