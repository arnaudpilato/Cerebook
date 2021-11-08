package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.UserRepository;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/userCreate")
    public String postUser(@ModelAttribute CerebookUser user) {
            userRepository.save(user);

        return "redirect:/";
    }

    @GetMapping("/users")
    public String getAll(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/cerebookUser/users";
    }

    @GetMapping("/user")
    public String getUser(Model model,
                            @RequestParam(required = false) Long id) {

        CerebookUser user = new CerebookUser();
        model.addAttribute("user", user);

        return "/cerebookUser/user";
    }
}