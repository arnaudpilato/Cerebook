package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import twitter4j.*;
import wcs.cerebook.controller.exception.illegalArgumentException;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookPostLike;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PostLikeRepository;
import wcs.cerebook.repository.PostRepository;
import wcs.cerebook.repository.UserRepository;
import wcs.cerebook.services.MediaService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import java.util.Date;
import java.util.List;


@Controller
public class PostController {
    @Autowired
    private PostRepository repository;

    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    @Autowired
    private PostLikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MediaService mediaService;

    @GetMapping("/postCreate")
    public String addPost(Principal principal, Model model) {
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        CerebookPost cerebookPost = new CerebookPost();
        model.addAttribute("post", cerebookPost);
        cerebookPost.setCerebookUser(user);
        model.addAttribute("user", user);
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));

        cerebookPost.setCreatedAt(new Date());
        model.addAttribute("localDateTime", new Date());
        //cerebookPost.setPrivatePost(cerebookPost.isPrivatePost());
        boolean postStatu = cerebookPost.isPrivatePost();
        model.addAttribute("postStatus", postStatu);

        return "cerebookPost/addPost";
    }

    //save post
    @RequestMapping("/save")
    public String savePost(Principal principal, @ModelAttribute() CerebookPost cerebookPost, CerebookPostLike cerebookPostLike, @RequestParam(value = "picture") MultipartFile picture) throws IOException {
        // save post to database
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        cerebookPost.setCerebookUser(user);

        if (!picture.isEmpty()) {
            if (cerebookPost.getId() != null) {
                if (!picture.isEmpty()) {
                    saveImage(cerebookPost, principal, picture);
                } else {
                    cerebookPost.setImage(repository.getById(cerebookPost.getId()).getImage());
                }
            } else {
                saveImage(cerebookPost, principal, picture);
            }
        }
        String filename = "static/css/data/" + picture.getOriginalFilename();

        try {
            mediaService.uploadPostImage(
                    filename,
                    picture.getInputStream(),
                    picture.getSize(),
                    user,
                    cerebookPost
            );
        } catch (IOException e) {
/*
            redirectAttributes.addAttribute("errorMessage", e.getMessage());
*/
        }
        cerebookPostLike.setCerebookPost(cerebookPost);
        cerebookPostLike.setLiked(true);
        cerebookPostLike.setCerebookUser(user);
        likeRepository.save(cerebookPostLike);
        Long postId = cerebookPost.getId();

        return "redirect:/profil";
    }

    @RequestMapping("/save/home")
    public String savePostHome(Principal principal, CerebookPost cerebookPost, CerebookPostLike cerebookPostLike) {
        // save post to database
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        cerebookPost.setCerebookUser(user);
        repository.save(cerebookPost);
        cerebookPostLike.setCerebookPost(cerebookPost);
        cerebookPostLike.setLiked(true);
        cerebookPostLike.setCerebookUser(user);
        likeRepository.save(cerebookPostLike);
        return "redirect:/actus";
    }

    private void saveImage(@ModelAttribute CerebookPost cerebookPost, Principal
            principal, @RequestParam("picture") MultipartFile image) throws IOException {
        Files.copy(image.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + principal.getName() + "_image_" + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        cerebookPost.setImage("/static/css/data/" + principal.getName() + "_image_" + image.getOriginalFilename());
    }

    // list all posts
    @RequestMapping("/posts")
    public String getAllPosts(Model model) {
        List<CerebookPost> cerebookPosts = repository.findAll();

        model.addAttribute("listPosts", cerebookPosts);

        return "cerebookPost/posts";
    }

    @RequestMapping("/editPost/{id}")
    public String showPostForm(@PathVariable("id") long id, Model model, Principal principal) throws
            illegalArgumentException {
        CerebookPost cerebookPost = this.repository.findById(id)
                .orElseThrow(() -> new illegalArgumentException(" Invalid post id: " + id));
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        model.addAttribute("post", cerebookPost);
        //cerebookPost.setCerebookUser(user);
        model.addAttribute("user", user);
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
        cerebookPost.setCreatedAt(new Date());
        model.addAttribute("localDateTime", new Date());
        boolean postStatu = cerebookPost.isPrivatePost();

        model.addAttribute("postStatus", postStatu);

        //model.addAttribute("posts",cerebookPost);
        return "cerebookPost/post";
    }

    @PostMapping("/updatePost/{id}")
    public String updatePost(@PathVariable("id") Long id, @Valid CerebookPost cerebookPost) {

        return "cerebookPost/allPosts";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable("id") Long id) throws illegalArgumentException {
        CerebookPost cerebookPost = this.repository.findById(id)
                .orElseThrow(() -> new illegalArgumentException(" Invalid post id: " + id));
        repository.delete(cerebookPost);

        return "redirect:/profil";
    }

    @GetMapping("/myPosts")
    public String getMyPosts(Model model, Principal principal, @Valid CerebookUser cerebookUser) {
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        model.addAttribute("listMyPosts", repository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
        return "cerebookPost/myPosts";
    }

    @GetMapping("/allPosts")
    public String getAllPosts(Model model, Principal principal, @Valid CerebookPost cerebookPost, CerebookUser
            cerebookUser) throws TwitterException {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        List<CerebookPostLike> cerebookPostLike = likeRepository.findAll();
        List<CerebookPost> cerebookPosts = repository.findAll();
        model.addAttribute("listPosts", cerebookPosts);
        model.addAttribute("user", user);
        model.addAttribute("userActual", userRepository.findByUsername(principal.getName()));
        model.addAttribute("like", cerebookPostLike);
        cerebookPost.setCreatedAt(new Date());
        model.addAttribute("localDateTime", new Date());
        boolean postStatu = cerebookPost.isPrivatePost();
        model.addAttribute("postStatus", postStatu);

        return "cerebookPost/allPosts";

    }

}
