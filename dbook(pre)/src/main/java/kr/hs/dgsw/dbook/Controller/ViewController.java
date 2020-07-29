package kr.hs.dgsw.dbook.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ViewController {

    @GetMapping("/")
    public ModelAndView mainPage(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/main.html");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login.html");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView signupPage(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/signup.html");
        return modelAndView;
    }

    @GetMapping("/upload")
    public ModelAndView uploadPage(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/upload.html");
        return modelAndView;
    }

    @GetMapping("/setting")
    public ModelAndView settingPage(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/setting.html");
        return modelAndView;
    }

}
