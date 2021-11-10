package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.UserRepository;


@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String login() {
        return "index";
    }

    @PostMapping("/messageCreate")
    public String postUser(@ModelAttribute CerebookUser user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
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