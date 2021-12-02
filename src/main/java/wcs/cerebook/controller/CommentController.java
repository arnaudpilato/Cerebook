package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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
        CerebookPost cerebookPost = postRepository.getById(postId);
        CerebookComment cerebookComment = new CerebookComment();
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        model.addAttribute("comment", cerebookComment);
        model.addAttribute("post", cerebookPost);
        model.addAttribute("time", new Date());
        return "cerebookComment/addComment";
    }

    //save  comment
    @RequestMapping("/savecomment")
    public String saveComment(CerebookPost cerebookPost, @ModelAttribute CerebookComment cerebookComment, Principal principal) {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());
        cerebookComment.setCerebookPost(cerebookPost);
        cerebookComment.setCerebookUser(user);
        cerebookComment.setCreatedAt(new Date());
        commentRepository.save(cerebookComment);
        return "redirect:/allPosts";
    }

    @GetMapping("/listComment/{postid}")
    public String showComment(@PathVariable("postid") Long postid, Model model, Principal principal) {
        List<CerebookUser> user = userRepository.findAll();
        List<CerebookComment> comments = postRepository.getById(postid).getComments();
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
        return "redirect:/listEventComment/"+cerebookEvent.getId();
    }

    @GetMapping("/listEventComment/{eventid}")
    public String showEventComment(@PathVariable("eventid") Long eventid, Model model, Principal principal) {
        List<CerebookUser> user = userRepository.findAll();
        List<CerebookComment> comments = eventRepository.getById(eventid).getComments();
        CerebookEvent event = eventRepository.getById(eventid);
        model.addAttribute("listComment", comments);
        model.addAttribute("user", user);

        model.addAttribute("event", event);

        return "cerebookComment/listEventComments";
    }
}
