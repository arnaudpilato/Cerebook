package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wcs.cerebook.entity.CerebookConfirmationFriend;
import wcs.cerebook.entity.CerebookFriend;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.ConfirmRepository;
import wcs.cerebook.repository.FriendRepository;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Controller
public class FirendController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private ConfirmRepository confirmRepository;



    @GetMapping("/addFriends")
    public String index(Model model, Principal principal) {
        // Récupération du user connecter
        String userCurrentName = principal.getName();
        CerebookUser currentUser = userRepository.getCerebookUserByUsername(userCurrentName);

        // Récupération des utilisateur en fonction de leurs ROLE
        List<CerebookUser> users = userRepository.getCerebookUserByRole(currentUser.getRole());

        model.addAttribute("users", users);

        return "cerebookFriends/index";
    }

    @RequestMapping("/add/friend/{id}")
    public String requestAddFriend(Principal principal, @PathVariable("id") Long id,
                            RedirectAttributes redirectAttributes
    ) {

        // je récupére le user que je veux ajouté a ma list d'amis
        CerebookUser userFriend = userRepository.getById(id);

        // je récupére le user connecté
        String currentUsername = principal.getName();
        CerebookUser currentUser = userRepository.getCerebookUserByUsername(currentUsername);

        CerebookFriend requestFriends = new CerebookFriend(currentUser, userFriend);

        CerebookConfirmationFriend confirmate = new CerebookConfirmationFriend(false, userFriend);
        confirmRepository.save(confirmate);
        requestFriends.setConfirmationFriend(confirmate);
        friendRepository.save(requestFriends);



        return "redirect:/addFriends";
    }
    @RequestMapping("/confirm/friend/{id}")
    public String requestComfirmFriend(Principal principal, @PathVariable("id") Long id,
                                   RedirectAttributes redirectAttributes
    ) {

        // je récupére le user que je veux ajouté a ma list d'amis
        CerebookUser userFriend = userRepository.getById(id);


        return "redirect:/addFriends";
    }
}
