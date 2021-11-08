package wcs.cerebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.repository.UserRepository;

@Controller
public class MessagingController {
private UserRepository userRepository;

    @GetMapping("/messaging")
    public String index() {
        return "messaging";
    }

}
