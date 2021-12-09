package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wcs.cerebook.entity.CerebookComment;
import wcs.cerebook.entity.CerebookFriend;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.FriendRepository;
import wcs.cerebook.repository.PostRepository;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CerebookHomeController {

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;


    @GetMapping("/actus")
    public String getCartography(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

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

        List<CerebookUser> friends = new ArrayList<>();

        List<CerebookFriend> confirmed = friendRepository.getByConfirmationFriend_Id
                (userRepository.getCerebookUserByUsername(principal.getName()));
        for (CerebookFriend friend: confirmed
        ) {
            friends.add(friend.getCurrentFriends());
        }

        List<CerebookFriend> confirmey = friendRepository.getByConfirmationFriendUser_Id
                (userRepository.getCerebookUserByUsername(principal.getName()));
        for (CerebookFriend friend: confirmey
        ) {
            friends.add(friend.getCurrentUser());
        }

        CerebookComment cerebookComment = new CerebookComment();
        model.addAttribute("user", user);
        model.addAttribute("comment", cerebookComment);
        model.addAttribute("post", cerebookPost);
        cerebookComment.setCreatedAt(new Date());
        model.addAttribute("time", new Date());

        List<CerebookPost> posts = postRepo.findAllByOrderByIdDesc();;
        Set<String> usernameSet = new TreeSet<>();
        usernameSet.addAll(friends.stream().map(CerebookUser::getUsername).collect(Collectors.toSet()));
        model.addAttribute("posts", posts);
        model.addAttribute("friendsUsername", usernameSet);

        return "home/index";
    }
}
