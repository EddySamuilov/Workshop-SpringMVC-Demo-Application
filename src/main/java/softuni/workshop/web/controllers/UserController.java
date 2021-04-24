package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.domain.models.binding.UserRegisterBindingModel;
import softuni.workshop.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {

        return super.view("user/register");
//        return new ModelAndView("user/register", "title", "login");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel userRegisterBindingModel) {

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return super.redirect("/users/register");
//            return new ModelAndView("redirect:/users/login");
        }

        this.userService.registerUser(userRegisterBindingModel);
        return super.redirect("/users/login");
//        return new ModelAndView("redirect:/users/login");
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return super.view("user/login");
    }
}
