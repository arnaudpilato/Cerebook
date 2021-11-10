package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;

@Controller
public class ProfilController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profil")
    public String getAll(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "/cerebookProfil/profil";
    }
}
