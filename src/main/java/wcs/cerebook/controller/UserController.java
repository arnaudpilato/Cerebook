package wcs.cerebook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import wcs.cerebook.entity.CerebookCartography;
import wcs.cerebook.entity.CerebookProfil;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String login() {
        return "index";
    }

    @GetMapping("/login")
    public String loginError() {
        return "index";
    }

    @RequestMapping("/userCreate")
    public String postUser(@ModelAttribute CerebookUser user, Model model,
                           @Param("confirm") String confirm, @Param("gpsx") Double gpsx, @Param("gpsy") Double gpsy
    ) {
        if (!confirm.equals(user.getPassword())) {
            boolean error = true;
            model.addAttribute("errorPassword", error);
            model.addAttribute("user", user);
            return "/cerebookUser/user";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        user.setPassword(encodedPassword);
        user.setProfil(new CerebookProfil());
        if (user.getRole().equals("HERO")) {
            user.getProfil().setBanner("/static/css/img/banner.png");
        } else if (user.getRole().equals("MECHANT")) {
            user.getProfil().setBanner("/static/css/img/magneto-banner.jpeg");
        }else {
            user.getProfil().setBanner("/static/css/img/New-York-Manhattan.jpeg");
        }
        user.getProfil().setAvatar("/static/css/img/avatar.jpeg");

        try {
            userRepository.save(user);
        } catch (Exception e) {
            boolean error = true;
            model.addAttribute("error", error);
            model.addAttribute("user", user);
            return "/cerebookUser/user";
        }

        return "redirect:/";
    }

    @GetMapping("/users")
    public String getAll(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "/cerebookUser/users";
    }

    @GetMapping("/user")
    public String getUser(Model model,
                          @RequestParam(required = false) Long id) {

        CerebookUser user = new CerebookUser();
        model.addAttribute("user", user);

        return "/cerebookUser/user";
    }
}