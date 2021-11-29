package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Controller
public class FirendController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/addFriends")
    public String index(Model model, Principal principal) {
        // Récupération du user connecter
        String userCurrentName = principal.getName();
        CerebookUser currentUser = userRepository.getCerebookUserByUsername(userCurrentName);

        // Récupération des utilisateur en fonction de leurs ROLE
        List<CerebookUser> users = userRepository.getCerebookUserByRole(currentUser.getRole());

        for (CerebookUser user: users
             ) {
            System.out.println(user.getUsername());
        }

        model.addAttribute("users", users);


        return "cerebookFriends/index";
    }

}
