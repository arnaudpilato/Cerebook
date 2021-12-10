package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.entity.CerebookVideo;
import wcs.cerebook.repository.UserRepository;
import wcs.cerebook.repository.VideoRepository;
import wcs.cerebook.services.MediaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
public class VideoController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private MediaService mediaService;

    @GetMapping("/video")
    public String getAllVideo(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        return "cerebookVideo/video";
    }

    @GetMapping("/video/show")
    public String showVideo(Model model, @RequestParam Long id, Principal principal) {
        // PIL : Récupération de l'user pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        model.addAttribute("video", videoRepository.findById(id));

        return "cerebookVideo/video_show";
    }

    @GetMapping("/video/update")
    public String updateVideo(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        return "cerebookVideo/video_update";
    }

    @PostMapping("/video/update")
    public String postVideoUpdate(@ModelAttribute CerebookVideo cerebookVideo,
                                  @RequestParam(value = "file_video") MultipartFile video,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) throws IOException {
        if (!video.isEmpty()) {
            String filename = "static/css/data/" + video.getOriginalFilename();
            CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
            Files.copy(video.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + video.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            cerebookVideo.setVideoPath("/static/css/data/" + video.getOriginalFilename());

            if (cerebookVideo.getId() == null) {
                cerebookVideo.setUser(userRepository.findByUsername(principal.getName()));
            }
            try {
                mediaService.uploadVideo(
                        filename,
                        video.getInputStream(),
                        video.getSize(),
                        user
                );
            } catch (IOException e) {
                redirectAttributes.addAttribute("errorMessage", e.getMessage());
            }
        }
        return "redirect:/video";
    }

    @GetMapping("/video/delete")
    public String deleteVideo(@RequestParam Long id) {
        videoRepository.deleteById(id);

        return "redirect:/video";
    }
}
