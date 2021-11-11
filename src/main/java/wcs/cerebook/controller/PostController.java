package wcs.cerebook.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PostRepository;
import wcs.cerebook.repository.UserRepository;


import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;


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
    private UserRepository userRepository;
    @GetMapping("/addPostForm")
    public String addPost(Principal principal,Model model) {
    String username = principal.getName();
    CerebookUser user = userRepository.getCerebookUserByUsername(username);
        CerebookPost cerebookPost = new CerebookPost();
        model.addAttribute("post", cerebookPost);
        cerebookPost.setCerebookUser(user);

        model.addAttribute("user",user);
        cerebookPost.setCreatedAt(new Date());
        model.addAttribute("localDateTime",new Date());
        //cerebookPost.setPrivatePost(cerebookPost.isPrivatePost());
        boolean postStatu = cerebookPost.isPrivatePost();

        model.addAttribute("postStatus", postStatu);

        return "addPost";
    }
    @RequestMapping("/savePost")
    public String savePost(Principal principal,CerebookPost cerebookPost) {
        // save post to database
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        cerebookPost.setCerebookUser(user);
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
            //postToUpdate.setCreatedAt(createdAt);
            postToUpdate.setPrivatePost(isPrivatePost);

        }
        return repository.save(postToUpdate);
    }
    @RequestMapping("/deletePost")
    public void deletePost(Long postId) {
        repository.deleteById(postId);
    }
}