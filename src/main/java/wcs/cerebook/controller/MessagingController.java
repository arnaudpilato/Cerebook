package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wcs.cerebook.entity.CerebookMessage;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.MessageRepository;
import wcs.cerebook.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class MessagingController  {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository msgRepository;

    @GetMapping("/messages")
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/message/messaging";
    }

    @GetMapping("/message")
    public String tchat( Model model, @RequestParam(required = false) String username) {

    model.addAttribute("user", userRepository.findByUsername(username));

        return "message/tchat";
    }


    @RequestMapping("/createTchatmessage")
    public String tchatSave ( Model model, Principal principal,
                              @Param("contentMessage") String contentMessage
                              )
    {


        String usernameCurrent = principal.getName();
        CerebookUser currentUser = userRepository.findByUsername(usernameCurrent);

        //Date a l'heure ou le message est envoyé + format correct
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = now.format(formatter);
        System.out.println(currentUser.getUsername());
        System.out.println(date);
        System.out.println(contentMessage);
//
        return "redirect:/message";
    }
}
