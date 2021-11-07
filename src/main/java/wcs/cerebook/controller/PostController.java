package wcs.cerebook.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.repository.PostRepository;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestController
public class PostController {
    @Autowired
    private PostRepository repository;
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/createPost")
    public CerebookPost createPost(Date createdAt,String content,Boolean isPrivatePost) {
        CerebookPost post = new CerebookPost();
        return repository.save(post);
    }

    @RequestMapping("/readPost")
    public CerebookPost getPost(Long postId) {
        return repository.findById(postId).get();
    }

    @RequestMapping("/readallPost")
    public List <CerebookPost> getAllPosts() {
        List<CerebookPost> cerebookPost = new ArrayList<>();
        repository.findAll().forEach(cerebookPost::add);
        return cerebookPost;
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
