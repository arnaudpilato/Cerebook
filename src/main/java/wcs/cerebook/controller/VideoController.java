package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wcs.cerebook.entity.CerebookVideo;
import wcs.cerebook.repository.UserRepository;
import wcs.cerebook.repository.VideoRepository;
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

    @GetMapping("/video")
    public String getAllVideo(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "cerebookVideo/video";
    }

    @GetMapping("/video/show")
    public String showVideo(Model model, @RequestParam Long id) {
        model.addAttribute("video", videoRepository.findById(id));

        return "cerebookVideo/video_show";
    }

    @GetMapping("/video/update")
    public String updateVideo(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "cerebookVideo/video_update";
    }

    @PostMapping("/video/update")
    public String postVideoUpdate(@ModelAttribute CerebookVideo cerebookVideo, @RequestParam(value = "file_video") MultipartFile video, Principal principal) throws IOException {
        if (!video.isEmpty()) {
            Files.copy(video.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + video.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            cerebookVideo.setVideoPath("/static/css/data/" + video.getOriginalFilename());
            if (cerebookVideo.getId() == null) {
                cerebookVideo.setUser(userRepository.findByUsername(principal.getName()));
            }
            videoRepository.save(cerebookVideo);
        }
        return "redirect:/video";
    }

    @GetMapping("/video/delete")
    public String deleteVideo(@RequestParam Long id) {
        videoRepository.deleteById(id);

        return "redirect:/video";
    }
}
