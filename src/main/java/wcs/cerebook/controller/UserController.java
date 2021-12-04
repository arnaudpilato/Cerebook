package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.entity.CerebookCartography;
import wcs.cerebook.entity.CerebookProfil;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.UserRepository;
import wcs.cerebook.services.GeocodeService;
import wcs.cerebook.services.CerebookUserService;

import java.security.Principal;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private CerebookUserService service;

    @GetMapping("/")
    public String login(Model model, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "index";
    }

    @GetMapping("/login")
    public String loginError() {
        return "index";
    }

    @RequestMapping("/userCreate")
    public String postUser(@ModelAttribute CerebookUser user, Model model,
                           @Param("confirm") String confirm, @Param("city") String city
    ) {
        if (!confirm.equals(user.getPassword())) {
            boolean error = true;
            model.addAttribute("errorPassword", error);
            model.addAttribute("user", user);
            return "cerebookUser/user";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        user.setPassword(encodedPassword);
        user.setProfil(new CerebookProfil());
        if (user.getRole().equals("HERO")) {
            user.getProfil().setBanner("/static/css/img/banner.png");
            user.getProfil().setOrnament("/static/css/img/ornament-good.png");
        } else if (user.getRole().equals("MECHANT")) {
            user.getProfil().setBanner("/static/css/img/magneto-banner.jpeg");
            user.getProfil().setOrnament("/static/css/img/ornament-bad.png");
        } else {
            user.getProfil().setBanner("/static/css/img/New-York-Manhattan.jpeg");
            user.getProfil().setOrnament("/static/css/img/ornament-neutral.png");
        }
        user.getProfil().setAvatar("/static/css/img/avatar.jpeg");


        try {
            user.setCartography(new CerebookCartography());
            user.getCartography().setX(geocodeService.getAdressAsJson(user.getCity() + " " + user.getAddress()).get("data").get(0).get("longitude").asDouble());
            user.getCartography().setY(geocodeService.getAdressAsJson(user.getCity() + " " + user.getAddress()).get("data").get(0).get("latitude").asDouble());
        } catch (Exception e) {
            boolean error_cartography = true;
            model.addAttribute("error_cartography", error_cartography);
            model.addAttribute("user", user);
            return "cerebookUser/user";
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            boolean error = true;
            model.addAttribute("error", error);
            model.addAttribute("user", user);
            return "cerebookUser/user";
        }

        return "redirect:/";
    }

    @GetMapping("/user")
    public String getUser(Model model, @RequestParam(required = false) Long id) {

        CerebookUser user = new CerebookUser();
        model.addAttribute("user", user);

        return "cerebookUser/user";
    }

    @RequestMapping("/users")
    public String viewUser(Model model, @Param("keyword") String keyword, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        List<CerebookUser> listUsers = service.listAll(keyword);
        CerebookUser actualUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("users", listUsers);
        model.addAttribute("actualUser", actualUser);
        model.addAttribute("keyword", keyword);

        return "cerebookUser/users";
    }
}
