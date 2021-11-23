package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wcs.cerebook.entity.CerebookEvent;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.EventRepository;
import wcs.cerebook.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/eventCreate")
    public String addPost(Principal principal, Model model) {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        CerebookEvent cerebookEvent = new CerebookEvent();
        model.addAttribute("event", cerebookEvent);
        cerebookEvent.setCerebookUser(user);

        model.addAttribute("user", user);

        return "/cerebookEvent/eventCreate";
    }


    @RequestMapping("/eventSave")
    public String savePost(@ModelAttribute CerebookEvent cerebookEvent, Principal principal, @RequestParam(value = "image_file") MultipartFile image) throws IOException {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        if (cerebookEvent.getId() != null) {
            if (!image.isEmpty()) {
                saveImage(cerebookEvent, principal, image);
            } else {
                cerebookEvent.setImage(eventRepository.getById(cerebookEvent.getId()).getImage());
            }
        } else {
            saveImage(cerebookEvent, principal, image);
        }
        cerebookEvent.setCerebookUser(user);
        eventRepository.save(cerebookEvent);

        return "redirect:/profil";
    }

    private void saveImage(@ModelAttribute CerebookEvent cerebookEvent, Principal principal, @RequestParam("image_file") MultipartFile image) throws IOException {
        Files.copy(image.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + principal.getName() + "_image_" + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        cerebookEvent.setImage("/static/css/data/" + principal.getName() + "_image_" + image.getOriginalFilename());
    }

    @RequestMapping("/events")
    public String getAllPosts(Model model) {
        List<CerebookEvent> cerebookEvent = eventRepository.findAll();

        model.addAttribute("events", cerebookEvent);

        return "/cerebookEvent/events";
    }
}
