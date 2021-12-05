package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wcs.cerebook.controller.exception.illegalArgumentException;
import wcs.cerebook.entity.CerebookComment;
import wcs.cerebook.entity.CerebookEvent;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.CommentRepository;
import wcs.cerebook.repository.EventRepository;
import wcs.cerebook.repository.PostRepository;
import wcs.cerebook.repository.UserRepository;

import javax.validation.Valid;
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
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/addComment/{postId}")
    public String addComment(@PathVariable("postId") Long postId, Model model, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        CerebookPost cerebookPost = postRepository.getById(postId);
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        CerebookComment cerebookComment = new CerebookComment();
        model.addAttribute("user", user);
        model.addAttribute("comment", cerebookComment);
        model.addAttribute("post", cerebookPost);
        cerebookComment.setCreatedAt(new Date());
        model.addAttribute("time", new Date());

        return "cerebookComment/addComment";

    }

    @PostMapping("update/{commentid}")
    public String updateComment(@PathVariable("commentid") long commentid, Model model) {
        CerebookComment cerebookComment = commentRepository.getById(commentid);
        if (cerebookComment != null) {
            cerebookComment.setId(commentid);
            return "redirect:/profil";
        }
        // mise a jour du commentaire
        commentRepository.save(cerebookComment);
        return "profil";
    }

    //save  comment
    @PostMapping("/savecomment")
    public String saveComment(@ModelAttribute CerebookComment cerebookComment,CerebookPost cerebookPost, Principal principal) {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        cerebookComment.setCerebookPost(cerebookPost);
        cerebookComment.setCerebookUser(user);
        cerebookComment.setCreatedAt(new Date());
        commentRepository.save(cerebookComment);

        return "redirect:/profil";
    }

    @GetMapping("/listComment/{postid}")
    public String showComment(@PathVariable("postid") Long postid, Model model) {
        List<CerebookUser> user = userRepository.findAll();
        List<CerebookComment> comments = commentRepository.findAll();
        List<CerebookPost> post = postRepository.findAll();
        model.addAttribute("listComment", comments);
        model.addAttribute("user", user);
        model.addAttribute("post", post);

        return "cerebookComment/listComments";
    }


    @GetMapping("/addEventComment/{eventid}")
    public String addEventComment(@PathVariable("eventid") Long eventid, Model model, Principal principal) {
        CerebookEvent cerebookEvent = eventRepository.getById(eventid);
        CerebookComment cerebookComment = new CerebookComment();
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        model.addAttribute("comment", cerebookComment);
        model.addAttribute("event", cerebookEvent);

        return "cerebookComment/addEventComment";
    }

    //save  comment
    @RequestMapping("/saveEventComment")
    public String saveEventComment(CerebookEvent cerebookEvent, @ModelAttribute CerebookComment cerebookComment, Principal principal) {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        cerebookComment.setCerebookEvent(cerebookEvent);
        cerebookComment.setCerebookUser(user);
        cerebookComment.setCreatedAt(new Date());
        commentRepository.save(cerebookComment);

        return "redirect:/listEventComment/" + cerebookEvent.getId();
    }

    @GetMapping("/listEventComment/{eventid}")
    public String showEventComment(@PathVariable("eventid") Long eventid, Model model) {
        List<CerebookUser> user = userRepository.findAll();
        List<CerebookComment> comments = eventRepository.getById(eventid).getComments();
        CerebookEvent event = eventRepository.getById(eventid);
        model.addAttribute("listComment", comments);
        model.addAttribute("user", user);
        model.addAttribute("event", event);

        return "cerebookComment/listEventComments";
    }
}
