package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.entity.CerebookCartography;
import wcs.cerebook.entity.CerebookFriend;
import wcs.cerebook.entity.CerebookProfil;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.FriendRepository;
import wcs.cerebook.repository.UserRepository;
import wcs.cerebook.services.GeocodeService;
import wcs.cerebook.services.CerebookUserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private CerebookUserService service;

    @Autowired
    private FriendRepository friendRepository;


    @GetMapping("/home")
    public String login(Model model, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        if (principal != null) {
            model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
        }

        return "cerebookUser/login";
    }

    @GetMapping("/login")
    public String loginError() {
        return "cerebookUser/login";
    }

    @RequestMapping("/userCreate")
    public String postUser(@ModelAttribute CerebookUser user, Model model,
                           @Param("confirm") String confirm, @Param("city") String city,
                           Principal principal
    ) {
        if (!confirm.equals(user.getPassword())) {
            boolean error = true;
            model.addAttribute("errorPassword", error);
            model.addAttribute("user", user);
            model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

            return "cerebookUser/user";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        user.setPassword(encodedPassword);
        user.setProfil(new CerebookProfil());
        if (user.getRole().equals("HERO")) {
            user.getProfil().setBanner("static/css/data/banner.png");
            user.getProfil().setOrnament("static/css/data/ornament-good.png");
        } else if (user.getRole().equals("MECHANT")) {
            user.getProfil().setBanner("static/css/data/magneto-banner.jpeg");
            user.getProfil().setOrnament("static/css/data/ornament-bad.png");
        } else {
            user.getProfil().setBanner("static/css/data/New-York-Manhattan.jpeg");
            user.getProfil().setOrnament("static/css/data/ornament-neutral.png");
        }
        user.getProfil().setAvatar("static/css/data/avatar.jpeg");


        try {
            user.setCartography(new CerebookCartography());
            user.getCartography().setX(geocodeService.getAdressAsJson(user.getCity() + " " + user.getAddress()).get("data").get(0).get("longitude").asDouble());
            user.getCartography().setY(geocodeService.getAdressAsJson(user.getCity() + " " + user.getAddress()).get("data").get(0).get("latitude").asDouble());
        } catch (Exception e) {
            boolean error_cartography = true;
            model.addAttribute("error_cartography", error_cartography);
            model.addAttribute("user", user);
            model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
            return "cerebookUser/user";
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            boolean error = true;
            model.addAttribute("error", error);
            model.addAttribute("user", user);
            model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
            return "cerebookUser/user";
        }

        return "redirect:/home";
    }

    @GetMapping("/user")
    public String getUser(Model model, @RequestParam(required = false) Long id, Principal principal) {

        CerebookUser user = new CerebookUser();
        model.addAttribute("user", user);
        if (principal != null) {
            model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
        } else {
            model.addAttribute("userActual", user);
        }
        return "cerebookUser/user";
    }

    @RequestMapping("/users")
    public String viewUser(Model model, @Param("keyword") String keyword, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        List<CerebookUser> listUsers = service.listAll(keyword);
        CerebookUser actualUser = userRepository.findByUsername(principal.getName());

        List<CerebookUser> friends = new ArrayList<>();

        List<CerebookFriend> confirmed = friendRepository.getByConfirmationFriend_Id(actualUser);
        for (CerebookFriend friend : confirmed
        ) {
            friends.add(friend.getCurrentFriends());
        }

        List<CerebookFriend> confirmey = friendRepository.getByConfirmationFriendUser_Id(actualUser);
        for (CerebookFriend friend : confirmey
        ) {
            friends.add(friend.getCurrentUser());
        }

        model.addAttribute("users", listUsers);
        model.addAttribute("actualUser", actualUser);
        model.addAttribute("keyword", keyword);
        Set<String> usernameSet = new TreeSet<>();
        usernameSet.addAll(friends.stream().map(CerebookUser::getUsername).collect(Collectors.toSet()));
        model.addAttribute("friendsUsername", usernameSet);

        return "cerebookUser/users";
    }
}