
package webproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import webproject.bean.DatabaseService;
import webproject.model.User;


@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    DatabaseService databaseService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("students", databaseService.listUsersByType(false));
        mv.addObject("lecturers", databaseService.listUsersByType(true));

        return mv;
    }
    
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addForm() {
        return "admin/add";
    }
    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(User u) {
        if (databaseService.createUser(u)) {
            return "redirect:/admin?status=add-user-failed";
        } else {
            return "redirect:/admin?status=add-user-success";
        }
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editForm(@RequestParam("username") String username) {
        User u = databaseService.findUserByUsername(username);
        if (u == null) {
            return new ModelAndView(new RedirectView("admin/edit", true));
        } else {
            ModelAndView mv = new ModelAndView("admin/edit");
            mv.addObject("userForm", u);
            return mv;
        }
    }
    
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String edit(User u) {
        databaseService.updateUser(u);
        return "redirect:/admin";
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ModelAndView deleteForm(@RequestParam("username") String username) {
        ModelAndView mv = new ModelAndView("/admin/delete");
        mv.addObject("username", username);
        return mv;
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam("username") String username) {
        databaseService.deleteUser(username);
        return "redirect:/admin";
    }

}
