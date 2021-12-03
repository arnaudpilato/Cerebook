package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wcs.cerebook.controller.exception.postNotFoundException;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookPostLike;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PostLikeRepository;
import wcs.cerebook.repository.PostRepository;
import wcs.cerebook.repository.UserRepository;

import java.security.Principal;


@Controller
public class CerebookPostLikeController {
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/addLike/{postid}")
    public String Like(@PathVariable("postid") Long postid, Principal principal, Model model) throws postNotFoundException {
        CerebookPost cerebookPost = postRepository.getById(postid);
        String username = principal.getName();
        CerebookUser user = userRepository.getCerebookUserByUsername(username);
        Long countpost = postLikeRepository.countCerebookLikeByPostId(postid);
        CerebookPostLike cerebookPostLike = new CerebookPostLike();
        model.addAttribute("post",cerebookPost);
        model.addAttribute("countLike",countpost);
        //user connecte a til deja liker
        // on verifie si il existe dans la table des likes par postid
        Long userIdLikePost = postLikeRepository.CerebookUserByLikeId(cerebookPostLike);
        System.out.println("========================================="+userIdLikePost+"---------------");
        if(userIdLikePost != null){
            return "cerebookPost/allPosts";
        }else {
            cerebookPostLike.setCerebookUser(user);
            cerebookPostLike.setCerebookPost(cerebookPost);
            postLikeRepository.save(cerebookPostLike);
        }


         return "cerebookPost/likePost";
    }

}


