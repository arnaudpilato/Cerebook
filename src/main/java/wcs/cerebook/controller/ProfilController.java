package wcs.cerebook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookProfil;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PostRepository;
import wcs.cerebook.repository.CartographyRepository;
import wcs.cerebook.repository.ProfilRepository;
import wcs.cerebook.repository.UserRepository;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfilController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CartographyRepository cartographyRepository;

    @GetMapping("/profil")
    public String getProfil(Model model, Principal principal,@Valid CerebookPost cerebookPost){
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        String username = principal.getName();
       CerebookUser user = userRepository.getCerebookUserByUsername(username);
        //List<CerebookUser> user = userRepository.findAll();
        List<CerebookPost> cerebookPosts = postRepository.findAll();
        model.addAttribute("listPosts", cerebookPosts);
        model.addAttribute("user", user);
        cerebookPost.setCreatedAt(new Date());
        model.addAttribute("localDateTime", new Date());
        boolean postStatu = cerebookPost.isPrivatePost();
        model.addAttribute("postStatus", postStatu);
        model.addAttribute("allUsers", userRepository.findAll());
        JsonNode json = new ObjectMapper().valueToTree(cartographyRepository.findAll());
        model.addAttribute("cartography", json);

        return "/cerebookProfil/profil";
    }

    @GetMapping("/profil/{id}")
    public String getOtherProfil(Model model, @PathVariable Long id) {
        model.addAttribute("user", userRepository.getById(id));

        return "/cerebookProfil/profil";
    }

    @GetMapping("/profil/update")
    public String getProfilUpdate(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "/cerebookProfil/profil_update";
    }

    @PostMapping("/profil/update")
    public String postProfilUpdate(@ModelAttribute CerebookProfil cerebookProfil, @RequestParam(value = "file_banner") MultipartFile banner, @RequestParam("file_avatar") MultipartFile avatar, Principal principal, Model model) throws IOException {
        if (cerebookProfil.getId() != null) {
            if (!banner.isEmpty()) {
                String bannerExtension = Optional.of(banner.getOriginalFilename()).filter(f -> f.contains(".")).map(f -> f.substring(banner.getOriginalFilename().lastIndexOf(".") + 1)).orElse("");
                Files.copy(banner.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + principal.getName() + "_banner." + bannerExtension), StandardCopyOption.REPLACE_EXISTING);
                cerebookProfil.setBanner("/static/css/data/" + principal.getName() + "_banner." + bannerExtension);
            } else {
                cerebookProfil.setBanner(profilRepository.getById(cerebookProfil.getId()).getBanner());
            }

            if (!avatar.isEmpty()) {
                String avatarExtension = Optional.of(avatar.getOriginalFilename()).filter(f -> f.contains(".")).map(f -> f.substring(avatar.getOriginalFilename().lastIndexOf(".") + 1)).orElse("");
                Files.copy(avatar.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + principal.getName() + "_avatar." + avatarExtension), StandardCopyOption.REPLACE_EXISTING);
                cerebookProfil.setAvatar("/static/css/data/" + principal.getName() + "_avatar." + avatarExtension);
            } else {
                cerebookProfil.setAvatar(profilRepository.getById(cerebookProfil.getId()).getAvatar());
            }

            profilRepository.save(cerebookProfil);
        }

        return "redirect:/profil";
    }

}
