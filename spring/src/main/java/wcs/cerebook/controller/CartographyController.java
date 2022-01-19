package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;

@Controller
public class  CartographyController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cartography")
    public String getCartography(Model model, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        model.addAttribute("users", userRepository.findAll());

        return "cerebookCartography/cartography";
    }
}
