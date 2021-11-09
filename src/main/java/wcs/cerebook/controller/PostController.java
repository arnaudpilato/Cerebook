package wcs.cerebook.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PostRepository;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Controller
public class PostController {
    @Autowired
    private PostRepository repository;
    @GetMapping("/addPostForm")
    public String addPost(Model model) {

        CerebookPost cerebookPost = new CerebookPost();
        model.addAttribute("post", cerebookPost);
        Date date = new DateTime();

        cerebookPost.setCreatedAt(date);
        model.addAttribute("localDateTime", date );
        model.addAttribute("postStatus",cerebookPost.isPrivatePost());
        return "CerebookPost/addPost";
    }
    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") CerebookPost cerebookPost, CerebookUser cerebookUser) {
        // save post to database
        repository.save(cerebookPost);
        return "redirect:/";
    }

    @RequestMapping("/readPost")
    public CerebookPost getPost(Long postId) {
        return repository.findById(postId).get();
    }

    @RequestMapping("/posts")
    public String getAllPosts(Model model) {
        List<CerebookPost> cerebookPosts = repository.findAll();

        model.addAttribute("listPosts",cerebookPosts);

        return "CerebookPost/posts";
    }

    @RequestMapping("/updatePost")
    public CerebookPost updatePost(Long postId,Date createdAt,String content,Boolean isPrivatePost) {
        CerebookPost postToUpdate = repository.findById(postId).get();
        if (postId != null) {
            postToUpdate.setContent(content);
            postToUpdate.setCreatedAt(createdAt);
            postToUpdate.setPrivatePost(isPrivatePost);

        }
        return repository.save(postToUpdate);
    }
    @RequestMapping("/deletePost")
    public void deletePost(Long postId) {
        repository.deleteById(postId);
    }
}