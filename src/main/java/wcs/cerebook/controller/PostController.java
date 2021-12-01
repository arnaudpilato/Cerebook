package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import twitter4j.*;
import wcs.cerebook.controller.exception.illegalArgumentException;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookPostLike;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PostLikeRepository;
import wcs.cerebook.repository.PostRepository;
import wcs.cerebook.repository.UserRepository;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    @GetMapping("/postCreate")
    public String addPost(Principal principal, Model model) {
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        CerebookPost cerebookPost = new CerebookPost();
        model.addAttribute("post", cerebookPost);
        cerebookPost.setCerebookUser(user);
        model.addAttribute("user", user);
        cerebookPost.setCreatedAt(new Date());
        model.addAttribute("localDateTime", new Date());
        //cerebookPost.setPrivatePost(cerebookPost.isPrivatePost());
        boolean postStatu = cerebookPost.isPrivatePost();

        model.addAttribute("postStatus", postStatu);

        return "/cerebookPost/addPost";
    }

    //save post
    @RequestMapping("/save")
    public String savePost(Principal principal, CerebookPost cerebookPost,CerebookPostLike cerebookPostLike) {
        // save post to database
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        cerebookPost.setCerebookUser(user);
        repository.save(cerebookPost);
        return "redirect:/profil";
    }


    // list all posts
    @RequestMapping("/posts")
    public String getAllPosts(Model model) {
        List<CerebookPost> cerebookPosts = repository.findAll();

        model.addAttribute("listPosts", cerebookPosts);

        return "/cerebookPost/posts";
    }

    @RequestMapping("/editPost/{id}")
    public String showPostForm(@PathVariable("id") long id, Model model, Principal principal) throws illegalArgumentException {
        CerebookPost cerebookPost = this.repository.findById(id)
                .orElseThrow(() -> new illegalArgumentException(" Invalid post id: " + id));
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        model.addAttribute("post", cerebookPost);
        //cerebookPost.setCerebookUser(user);
        model.addAttribute("user", user);
        cerebookPost.setCreatedAt(new Date());
        model.addAttribute("localDateTime", new Date());
        boolean postStatu = cerebookPost.isPrivatePost();

        model.addAttribute("postStatus", postStatu);

        //model.addAttribute("posts",cerebookPost);
        return "/cerebookPost/post";
    }

    @PostMapping("/updatePost/{id}")
    public String updatePost(@PathVariable("id") Long id, @Valid CerebookPost cerebookPost) {

        return"/allPosts";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable("id") Long id, Model model) throws illegalArgumentException {
        CerebookPost cerebookPost = this.repository.findById(id)
                .orElseThrow(() -> new illegalArgumentException(" Invalid post id: " + id));
        this.repository.delete(cerebookPost);
        model.addAttribute("posts", this.repository.findAll());
        return "/cerebookPost/posts";
    }

    @GetMapping("/myPosts")
    public String getMyPosts(Model model, Principal principal, @Valid CerebookUser cerebookUser) {
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        model.addAttribute("listMyPosts", repository.findAll());
        model.addAttribute("user", user);
        return "cerebookPost/myPosts";
    }

    @GetMapping("/allPosts")
    public String getAllPosts(Model model, Principal principal, @Valid CerebookPost cerebookPost, CerebookUser cerebookUser,CerebookPostLike cerebookPostLike) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        List<CerebookPost> cerebookPosts = repository.findAll();

        model.addAttribute("listPosts", cerebookPosts);
        model.addAttribute("user", user);
        model.addAttribute("like",cerebookPostLike.getCountLike());
        model.addAttribute("dislike",cerebookPostLike.getCountdisLike());
        cerebookPost.setCreatedAt(new Date());
        model.addAttribute("localDateTime", new Date());
        boolean postStatu = cerebookPost.isPrivatePost();
        model.addAttribute("postStatus", postStatu);


        //twitter
        /*try {
            // gets Twitter instance with default credentials
            Twitter twitter = new TwitterFactory().getInstance();
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        Twitter twitter = TwitterFactory.getSingleton();
        List<Status> tweets = twitter.getHomeTimeline();
        model.addAttribute("tweet", tweets );*/

        return "/allPosts";


    }



}