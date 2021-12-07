package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wcs.cerebook.controller.exception.illegalArgumentException;
import wcs.cerebook.entity.CerebookEvent;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.EventRepository;
import wcs.cerebook.repository.UserRepository;
import wcs.cerebook.services.GeocodeService;
import wcs.cerebook.services.MediaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GeocodeService geocodeService;
    @Autowired
    private MediaService mediaService;

    @GetMapping("/eventCreate")
    public String addPost(Principal principal, Model model, @RequestParam(required = false, value = "id") Long id) {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        CerebookEvent cerebookEvent = new CerebookEvent();

        if (id != null) {
            cerebookEvent = eventRepository.getById(id);

        }

        model.addAttribute("event", cerebookEvent);
        model.addAttribute("user", user);

        return "/cerebookEvent/eventCreate";
    }


    @RequestMapping("/eventSave")
    public String saveEvent(Model model, @ModelAttribute CerebookEvent cerebookEvent, Principal principal, @RequestParam(value = "picture") MultipartFile picture) throws IOException {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        try {
            cerebookEvent.setX(geocodeService.getAdressAsJson(cerebookEvent.getCity() + " " + cerebookEvent.getAddress()).get("data").get(0).get("longitude").asDouble());
            cerebookEvent.setY(geocodeService.getAdressAsJson(cerebookEvent.getCity() + " " + cerebookEvent.getAddress()).get("data").get(0).get("latitude").asDouble());
        } catch (Exception e) {
            boolean error_cartography = true;
            model.addAttribute("event", cerebookEvent);
            model.addAttribute("user", user);
            model.addAttribute("error_cartography", error_cartography);
            return "/cerebookEvent/eventCreate";
        }
        if (!picture.isEmpty()) {
            if (cerebookEvent.getId() != null) {
                if (!picture.isEmpty()) {
                    saveImage(cerebookEvent, principal, picture);
                } else {
                    cerebookEvent.setImage(eventRepository.getById(cerebookEvent.getId()).getImage());
                }
            } else {
                saveImage(cerebookEvent, principal, picture);
            }
        }
        String filename = "static/css/data/" + picture.getOriginalFilename();

        try {
            mediaService.uploadEventImage(
                    filename,
                    picture.getInputStream(),
                    picture.getSize(),
                    user,
                    cerebookEvent
            );
        } catch (IOException e) {
/*
            redirectAttributes.addAttribute("errorMessage", e.getMessage());
*/
        }


        Long eventId = cerebookEvent.getId();

        return "redirect:/event/" + eventId;
    }

    private void saveImage(@ModelAttribute CerebookEvent cerebookEvent, Principal principal, @RequestParam("image_file") MultipartFile image) throws IOException {
        Files.copy(image.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + principal.getName() + "_image_" + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        cerebookEvent.setImage("/static/css/data/" + principal.getName() + "_image_" + image.getOriginalFilename());
    }

    @RequestMapping("/events")
    public String getAllEvents(Model model, Principal principal) {
        List<CerebookEvent> cerebookEvent = eventRepository.findAll();
        CerebookUser cerebookUser = userRepository.getCerebookUserByUsername(principal.getName());

        model.addAttribute("events", cerebookEvent);
        model.addAttribute("user", cerebookUser);

        return "/cerebookEvent/events";
    }

    @GetMapping("/event/{id}")
    public String getEventById(Model model, @PathVariable Long id, Principal principal) {
        model.addAttribute("event", eventRepository.getById(id));
        model.addAttribute("user", userRepository.getCerebookUserByUsername(principal.getName()));
        return "/cerebookEvent/event";
    }

    @RequestMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable("id") Long id, Principal principal) throws illegalArgumentException {
        CerebookUser cerebookUser = userRepository.getCerebookUserByUsername(principal.getName());
        CerebookEvent cerebookEvent = eventRepository.findById(id)
                .orElseThrow(() -> new illegalArgumentException(" Invalid event id: " + id));
        if (cerebookEvent.getCerebookUser().getId().equals(cerebookUser.getId())) {
            eventRepository.delete(cerebookEvent);
        }
        return "redirect:/events";

    }
}
