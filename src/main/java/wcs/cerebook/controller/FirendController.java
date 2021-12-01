package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import java.util.ArrayList;
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

        // je crée l'objet Friend au quel je passe en parmétres le user current et son futur amis
        CerebookFriend requestFriends = new CerebookFriend(currentUser, userFriend);
        // je crée par la même occasion l'objet de confirmation de la demande d'amis
        CerebookConfirmationFriend confirmate = new CerebookConfirmationFriend(false, userFriend);
        confirmRepository.save(confirmate);
        // et je fait la relation entre les 2 objets en ajoutant la clef primaire que je sauvegarde dans la table friend
        requestFriends.setConfirmationFriend(confirmate);
        friendRepository.save(requestFriends);


        return "redirect:/addFriends";
    }

    @RequestMapping("/confirm/friends")
    public String requestComfirmFriend(Principal principal, Model model
    ) {

        // je récupére le user que je veux ajouté a ma list d'amis
        String userCurrentName = principal.getName();
        CerebookUser currentUser = userRepository.getCerebookUserByUsername(userCurrentName);
        // je récupére l'objet de confirmation FALSE en relation avec la table Friend par rapport au user connecter
        List<CerebookConfirmationFriend> notConfirmationFriend = confirmRepository.getByUserFriendId(currentUser);
        // je crée une nouvelle liste de type friend
        List<CerebookFriend> friends = new ArrayList<>();
        // je boucle sur l'objet qui contient les amis non confirmé que le user posséde pour faire une
        // recherche personalisé via une requette qui va récupérer tous les amis non confirmé via la clef primaire
        // que j'ajoute à ma liste et renvoie ensuite cette liste dans la vue
        for (CerebookConfirmationFriend notConfirm: notConfirmationFriend
             ) {
            CerebookFriend searchFriendFalse = friendRepository.getByConfirmationFriend_Id(notConfirm);
           friends.add(searchFriendFalse);
            }

        model.addAttribute("friends", friends);

        return "cerebookFriends/confirm";
    }


    // méthode pour confirmer l'amis en question
    @RequestMapping("/confirm/friend")
    public String requestComfirmTrueFriend(Principal principal, Model model, @RequestParam(required = true) Long id
    ) {
        CerebookFriend friend = friendRepository.getById(id);
        friend.getConfirmationFriend().setAdd(true);
        friendRepository.save(friend);

        return "redirect:/confirm/friends";
    }
}
