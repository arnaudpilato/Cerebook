package wcs.cerebook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wcs.cerebook.entity.CerebookMessage;
import wcs.cerebook.entity.CerebookProfil;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.CartographyRepository;
import wcs.cerebook.repository.PictureRepository;
import wcs.cerebook.repository.ProfilRepository;
import wcs.cerebook.repository.UserRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfilController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private CartographyRepository cartographyRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping("/profil")
    public String getProfil(Model model, Principal principal) throws JsonProcessingException {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("allUsers", userRepository.findAll());
        JsonNode json = new ObjectMapper().valueToTree(cartographyRepository.findAll());


        String userName = principal.getName();
        CerebookUser userId = userRepository.findByUsername(userName);

        //model.addAttribute("pictures", pictureRepository.lastPicture(userId.getId()));
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

    @GetMapping("/profil/picture/delete")
    public String deletePicture(@RequestParam Long id) {
        pictureRepository.deleteById(id);

        return "redirect:/profil";
    }
}
