package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;


@Controller
public class IndexController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        if (principal != null) {
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
        }
        return "index";
    }
}
