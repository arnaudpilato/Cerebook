package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wcs.cerebook.entity.CerebookPicture;
import wcs.cerebook.repository.PictureRepository;
import wcs.cerebook.repository.UserRepository;

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

    @GetMapping("/picture")
    public String getAllPicture(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "/cerebookPicture/picture";
    }

    @GetMapping("/picture/show")
    public String showPicture(Model model, @RequestParam Long id) {
        model.addAttribute("picture", pictureRepository.findById(id));

        return "/cerebookPicture/picture_show";
    }

    @GetMapping("/picture/update")
    public String updatePicture(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "/cerebookPicture/picture_update";
    }

    @PostMapping("/picture/update")
    public String postProfilUpdate(@ModelAttribute CerebookPicture cerebookPicture, @RequestParam(value = "file_picture") MultipartFile picture, Principal principal) throws IOException {

        Files.copy(picture.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + picture.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                cerebookPicture.setPicturePath("/static/css/data/" + picture.getOriginalFilename());
                if (cerebookPicture.getId() == null) {
                    cerebookPicture.setUser(userRepository.findByUsername(principal.getName()));
                }
        pictureRepository.save(cerebookPicture);
        return "redirect:/picture";
    }

    @GetMapping("/picture/delete")
    public String deletePicture(@RequestParam Long id) {
        pictureRepository.deleteById(id);

        return "redirect:/picture";
    }
}
