package webproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webproject.bean.DatabaseService;
import webproject.model.Lecture;
import webproject.model.User;

@Controller
public class IndexController {

    @Autowired
    DatabaseService databaseService;

    @RequestMapping("/")
    public String index(ModelMap model) {
        List<Lecture> lecture = databaseService.listLectures();
        model.addAttribute("lecture", lecture);
        return "index";
    }

    @RequestMapping("login")
    public String login() {
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
