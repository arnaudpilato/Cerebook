package wcs.cerebook.controller;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import wcs.cerebook.controller.exception.postNotFoundException;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookPostLike;
import wcs.cerebook.repository.PostLikeRepository;
import wcs.cerebook.repository.PostRepository;

import java.util.List;


@Controller
public class CerebookPostLikeController {
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;

    /*@PostMapping("/addLike/{postid}")
    public String Like(@PathVariable("postid") long postid) throws postNotFoundException {

        CerebookPost cerebookPost = postRepository.getById(postid);
        List<CerebookPostLike> cerebookPostLike = postLikeRepository.findAll();
        if (cerebookPostLike.isEmpty()) {
            CerebookPostLike newcerebookPostLike = new CerebookPostLike();
            long countLike = newcerebookPostLike.getCountLike() + 1;
            newcerebookPostLike.setCountLike(countLike);
            newcerebookPostLike.setLiked(true);
            postLikeRepository.save(cerebookPostLike);
        } else {

            System.out.println(postId.getId());
            CerebookPostLike cerebookPostLike = postLikeRepository.get;
            long countLike = cerebookPostLike.getCountLike() - 1;
            cerebookPostLike.setCountLike(countLike);
        }
        cerebookPostLike.setCerebookPost(cerebookPost);
        postLikeRepository.save(cerebookPostLike);
        return "redirect:/allPosts";
    }*/
}


