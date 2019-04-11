package webproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import webproject.bean.DatabaseService;
import webproject.model.User;

import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    DatabaseService databaseService;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("polls", databaseService.getPollList());

        return mv;
    }

    @RequestMapping("login")
    public String login(Principal principal) {
        if(principal != null) {
            return "redirect:/";
        }

        return "login";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String createAccount(RegisterForm form) {
        if (databaseService.createUser(new User(form.getUsername(), form.getPassword(), false))) {
            return "redirect:/register?status=register-success";
        } else {
            return "redirect:/register?status=register-failed";
        }
    }

    static class RegisterForm {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
