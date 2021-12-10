package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wcs.cerebook.entity.CerebookPicture;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PictureRepository;
import wcs.cerebook.repository.UserRepository;
import wcs.cerebook.services.MediaService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
public class PictureController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private MediaService mediaService;

    @GetMapping("/picture")
    public String getAllPicture(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        return "cerebookPicture/picture";
    }

    @GetMapping("/picture/show")
    public String showPicture(Model model, @RequestParam Long id, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        model.addAttribute("picture", pictureRepository.findById(id));

        return "cerebookPicture/picture_show";
    }

    @GetMapping("/picture/update")
    public String updatePicture(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        return "cerebookPicture/picture_update";
    }

    @PostMapping("/picture/update")
    public String postProfilUpdate(@ModelAttribute CerebookPicture cerebookPicture,
                                   @RequestParam(value = "file_picture") MultipartFile picture,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes) throws IOException {
        if (!picture.isEmpty()) {
            String filename = "static/css/data/" +picture.getOriginalFilename();
            CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
            Files.copy(picture.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + picture.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            cerebookPicture.setPicturePath("/static/css/data/" + picture.getOriginalFilename());

            if (cerebookPicture.getId() == null) {
                cerebookPicture.setUser(userRepository.findByUsername(principal.getName()));
            }

            try {
                mediaService.uploadPicture(
                        filename,
                        picture.getInputStream(),
                        picture.getSize(),
                        user
                );
            } catch (IOException e) {
                redirectAttributes.addAttribute("errorMessage", e.getMessage());
            }
        }

        return "redirect:/picture";
    }

    @GetMapping("/picture/delete")
    public String deletePicture(@RequestParam Long id) {
        pictureRepository.deleteById(id);

        return "redirect:/picture";
    }
}
