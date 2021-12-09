package wcs.cerebook.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import twitter4j.*;
import wcs.cerebook.entity.*;
import wcs.cerebook.model.MyUserDetails;
import wcs.cerebook.repository.*;
import wcs.cerebook.services.MediaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/profil")
    public String getProfil(Model model, Principal principal) {

        final List<Object> getAllPrincipals = sessionRegistry.getAllPrincipals();
        List<CerebookUser> usersConnected = new ArrayList<>();

        for (final Object principalConnect : getAllPrincipals) {
            if (principalConnect instanceof MyUserDetails) {
                final MyUserDetails myUserDetails = (MyUserDetails) principalConnect;
                List<SessionInformation> activeUserSessions =
                        sessionRegistry.getAllSessions(principalConnect,
                                /* includeExpiredSessions */ false); // Should not return null;
                if (!activeUserSessions.isEmpty()) {
                    usersConnected.add(userRepository.findByUsername(myUserDetails.getUsername()));
                }
            }
        }

        model.addAttribute("usersConnected", usersConnected);

        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        List<CerebookPost> cerebookPosts = user.getCerebookPosts();
        model.addAttribute("listPosts", cerebookPosts);
        model.addAttribute("localDateTime", new Date());
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("pictures", pictureRepository.lastPicture(user.getId()));
        model.addAttribute("videos", videoRepository.lastVideo(user.getId()));

        // PIL : Récupération du dernier message des 3 derniers amis
        List<Long[]> messagesFromSQL = messageRepository.lastThreeMessages(user.getId());
        List<CerebookMessage> messages = new ArrayList<>();

        // PIL : Récupération des 6 derniers films
        model.addAttribute("movies", movieRepository.lastMovie(user.getId()));

        for (Long[] ids : messagesFromSQL) {
            messages.add(messageRepository.getById(ids[0]));
        }
        model.addAttribute("cerebookMessages", messages);

        // PIL : Récupération des données json longitude et latitude
        List<CerebookCartography> cartographies = cartographyRepository.findAll();
        JsonNode json = new ObjectMapper().valueToTree(cartographies);
        model.addAttribute("cartography", json);
        // PIL : Récupérations des 6 derniers amis
        List<CerebookUser> friends = new ArrayList<>();
        List<CerebookFriend> confirmed = friendRepository.getLastFriend(user);
        for (CerebookFriend friend : confirmed) {
            friends.add(friend.getCurrentFriends());
        }
        model.addAttribute("friends", friends);
        //tweet
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            User twitterUser = twitter.verifyCredentials();
            if (user.getUsername().equals(twitterUser.getScreenName())) {
                List<Status> statuses = twitter.getUserTimeline();
                model.addAttribute("tweet", statuses);
                model.addAttribute("twitterUser", twitterUser.getScreenName());

            }

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }

        return "cerebookProfil/profil";

    }

    @GetMapping("/profil/{id}")
    public String getOtherProfil(Model model, @PathVariable Long id) {
        model.addAttribute("user", userRepository.getById(id));
        CerebookUser user = userRepository.getById(id);
        List<CerebookPost> cerebookPosts = user.getCerebookPosts();
        model.addAttribute("listPosts", cerebookPosts);
        model.addAttribute("localDateTime", new Date());
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("pictures", pictureRepository.lastPicture(user.getId()));
        model.addAttribute("videos", videoRepository.lastVideo(user.getId()));
        List<CerebookCartography> cartographies = cartographyRepository.findAll();
        JsonNode json = new ObjectMapper().valueToTree(cartographies);
        model.addAttribute("cartography", json);

        return "cerebookProfil/profil";
    }

    @GetMapping("/profil/update")
    public String getProfilUpdate(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "cerebookProfil/profil_update";
    }

    @PostMapping("/profil/update")
    public String postProfilUpdate(@ModelAttribute CerebookProfil cerebookProfil, @ModelAttribute CerebookUser cerebookUser, @RequestParam(value = "file_banner") MultipartFile banner, @RequestParam("file_avatar") MultipartFile avatar, Principal principal) throws IOException {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        if (cerebookProfil.getId() != null) {
            if (!banner.isEmpty()) {
                String bannerExtension = Optional.of(banner.getOriginalFilename()).filter(f -> f.contains(".")).map(f -> f.substring(banner.getOriginalFilename().lastIndexOf(".") + 1)).orElse("");
                String bannerName = "static/css/data/" + principal.getName() + "_banner." + bannerExtension;
                Files.copy(banner.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + principal.getName() + "_banner." + bannerExtension), StandardCopyOption.REPLACE_EXISTING);
                cerebookProfil.setBanner("static/css/data/" + principal.getName() + "_banner." + bannerExtension);
                try {
                    mediaService.uploadBanner(
                            bannerName,
                            banner.getInputStream(),
                            banner.getSize(),
                            user
                    );
                } catch (IOException e) {
/*
                redirectAttributes.addAttribute("errorMessage", e.getMessage());
*/
                }
            } else {
                cerebookProfil.setBanner(profilRepository.getById(cerebookProfil.getId()).getBanner());
            }

            if (!avatar.isEmpty()) {
                String avatarExtension = Optional.of(avatar.getOriginalFilename()).filter(f -> f.contains(".")).map(f -> f.substring(avatar.getOriginalFilename().lastIndexOf(".") + 1)).orElse("");
                String avatarName = "static/css/data/" + principal.getName() + "_avatar." + avatarExtension;
                Files.copy(avatar.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + principal.getName() + "_avatar." + avatarExtension), StandardCopyOption.REPLACE_EXISTING);
                cerebookProfil.setAvatar("static/css/data/" + principal.getName() + "_avatar." + avatarExtension);
                try {
                    mediaService.uploadBanner(
                            avatarName,
                            avatar.getInputStream(),
                            avatar.getSize(),
                            user
                    );
                } catch (IOException e) {
/*
                redirectAttributes.addAttribute("errorMessage", e.getMessage());
*/
                }
            } else {
                cerebookProfil.setAvatar(profilRepository.getById(cerebookProfil.getId()).getAvatar());
            }
            String ornament = user.getProfil().getOrnament();
            cerebookProfil.setOrnament(ornament);
            profilRepository.save(cerebookProfil);
        }

        return "redirect:/profil";
    }

    @GetMapping("/profil/picture/delete")
    public String deletePicture(@RequestParam Long id) {
        pictureRepository.deleteById(id);

        return "redirect:/profil";
    }

    @GetMapping("/profil/video/delete")
    public String deleteVideo(@RequestParam Long id) {
        videoRepository.deleteById(id);

        return "redirect:/profil";
    }
}
