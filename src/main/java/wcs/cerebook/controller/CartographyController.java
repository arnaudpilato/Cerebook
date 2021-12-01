package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.repository.UserRepository;

@Controller
public class CartographyController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cartography")
    public String getCartography(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "cerebookCartography/cartography";
    }
}
