package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import wcs.cerebook.controller.exception.postNotFoundException;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookPostLike;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PostLikeRepository;
import wcs.cerebook.repository.PostRepository;

import java.util.Optional;


@Controller
public class CerebookPostLikeController {
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/addLike/{id}")
    public String Like(@PathVariable("id") Long id,CerebookPostLike cerebookPostLike,CerebookPost cerebookPost) throws postNotFoundException {
        CerebookPost cerebookPostid = postRepository.getById(cerebookPost.getId());
        if (cerebookPost != null) {
            cerebookPostLike.setCerebookPost(cerebookPostid);
            long counter =cerebookPostLike.getCountdisLike()+1;
            cerebookPostLike.setCountLike(counter);
            cerebookPostLike.setLiked(true);
            postLikeRepository.save(cerebookPostLike);

        } else {
            throw new postNotFoundException(String.format("cet id na pas été trouvé !"+id));

        }
        return "redirect:/allPosts";
    }
}


