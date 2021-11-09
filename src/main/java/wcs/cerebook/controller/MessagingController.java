package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.UserRepository;

import java.util.Optional;

@Controller
public class MessagingController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/messages")
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        System.out.println("truc");
        return "/message/messaging";
    }


    @GetMapping("/message")
    public String tchat( @RequestParam(required = false) String username) {

        return "message/tchat";
    }

}
