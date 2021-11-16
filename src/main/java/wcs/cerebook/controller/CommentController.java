package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wcs.cerebook.controller.exception.illegalArgumentException;
import wcs.cerebook.entity.CerebookComment;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.CommentRepository;
import wcs.cerebook.repository.PostRepository;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;
import java.util.Date;
import java.util.List;


@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/addComment/{postId}")
    public String addComment(@PathVariable("postId") Long postId, Model model) {
        CerebookPost cerebookPost = postRepository.getById(postId);
        CerebookComment cerebookComment = new CerebookComment();
        model.addAttribute("comment", cerebookComment);
        model.addAttribute("post" ,cerebookPost );
        cerebookComment.setCreatedAt(new Date());
        model.addAttribute("time",new Date());
        return "addComment";
    }
    //save  comment
    @RequestMapping("/savecomment")
    public String saveComment(CerebookPost cerebookPost,CerebookComment cerebookComment) {
        cerebookComment.setCerebookPost(cerebookPost);

        commentRepository.save(cerebookComment);
        return "redirect:/";
    }
    @GetMapping("/listComment/{id}")
    public String showComment(Long id,Model model,Principal principal){
        String username = principal.getName();
        List<CerebookUser> user = userRepository.findAll();
        List<CerebookComment> comments = commentRepository.findAll();
        List<CerebookPost> post = postRepository.findAll();
        model.addAttribute("listComment", comments);
        model.addAttribute("user",user);
    model.addAttribute("post",post);

        return "listComments";
    }
}
