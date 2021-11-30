package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wcs.cerebook.entity.CerebookMessage;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.MessageRepository;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;
import java.util.*;

@Controller
public class MessagingController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository msgRepository;


    @GetMapping("/messages")
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "cerebookMessage/messaging";
    }

    @GetMapping("/message")
    public String tchat(Model model, @RequestParam(required = true) String username,
                        Principal principal) {
        // récupération du user connecter
        String usernameCurrent = principal.getName();
        CerebookUser currentUser = userRepository.findByUsername(usernameCurrent);
        // user destinataire
        CerebookUser userDestinate = userRepository.getCerebookUserByUsername(username);
        // pour récupéré tous les messages envoyé par rapport au user connecter
        List<CerebookMessage> sendMessages = msgRepository.getCerebookMessageByCurrentUseraAndUserDestination(currentUser,
                userDestinate);
        // pour récupéré tous les messages envoyer par rapport a l'utilisateur voulu
        List<CerebookMessage> messagesRecep = msgRepository.getCerebookMessageByCurrentUseraAndUserDestination(userDestinate,
                currentUser);
        //j'instancie une nouvelle list a la quelle j'ajoute tous les messages envoyé et recus
        List<CerebookMessage> finalList = new ArrayList<>();

        for (CerebookMessage msg : sendMessages
        ) {
            finalList.add(msg);
        }
        for (CerebookMessage msg : messagesRecep
        ) {
            finalList.add(msg);
        }
        // grace au comparator je trie les messages par date
        Comparator<CerebookMessage> comparator = new Comparator<CerebookMessage>() {
            @Override
            public int compare(CerebookMessage o1, CerebookMessage o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };

        // j'utilise le collections.sort() pour tiré la list
        Collections.sort(finalList, comparator);
        model.addAttribute("user", userDestinate);
        // j'envoie la list des messages trié dans le front
        model.addAttribute("messages", finalList);


        return "cerebookMessage/tchat";
    }


    @RequestMapping("/createTchatmessage")
    public String tchatSave(Model model, Principal principal,
                            @Param("userfriend") Long userfriend,
                            @Param("contentMessage") String contentMessage,
                            RedirectAttributes redirectAttributes
    ) {
        // récupération du user connecter
        String usernameCurrent = principal.getName();
        CerebookUser currentUser = userRepository.findByUsername(usernameCurrent);
        //Date a l'heure ou le message est envoyé
/*
        Date now = new Date(Calendar.getInstance().getTime().getTime());
*/
        //récupération du user qui va etre le destinataire du message
        CerebookUser userDestinate = userRepository.getById(userfriend);
        // sauvegarde du message en base de donnée
        if (!contentMessage.isEmpty()){
            CerebookMessage message = new CerebookMessage(contentMessage, new Date(), currentUser);
            msgRepository.save(message);
            // une fois le message sauvegarder j'ajoute le destinataire au message et je le sauvegarde une nouvelle fois
            // en base de donnée.
            message.getUserDestination().add(userDestinate);
            msgRepository.save(message);
        }


        redirectAttributes.addAttribute("usernameDestinate", userDestinate.getUsername());


        return "redirect:/message?username={usernameDestinate}";
    }
}
