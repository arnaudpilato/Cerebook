package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import wcs.cerebook.controller.exception.postNotFoundException;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookPostLike;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.PostLikeRepository;
import wcs.cerebook.repository.PostRepository;

import java.util.List;
import java.util.Optional;


@Controller
public class CerebookPostLikeController {
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/addLike/{postid}")
    public String Like(@PathVariable("postid") Long postid, CerebookPostLike cerebookPostLike) throws postNotFoundException{
            CerebookPost cerebookPost=postRepository.getById(postid);
            if(cerebookPost != null){
                cerebookPostLike.setCerebookPost(cerebookPost);
                postLikeRepository.save(cerebookPostLike);
            }else {
                throw new postNotFoundException(String.format("cet post na pas été trouvé !"+postid));
            }
        return "redirect:/allPosts";
    }
    @PostMapping("/updateLike/{id}")
    public String updateLike(@PathVariable("id") Long id) throws postNotFoundException {
        CerebookPostLike cerebookPostLike = postLikeRepository.getById(id);
        if (cerebookPostLike != null) {
            boolean liked = cerebookPostLike.isLiked();
            long countLike = cerebookPostLike.getCountLike();
            if(liked=true && countLike > 1){
                countLike = cerebookPostLike.getCountLike()-1;
                cerebookPostLike.setLiked(false);
                        }if (liked=false){
                            countLike = cerebookPostLike.getCountLike()+1;
                            cerebookPostLike.setLiked(true);
                }
            cerebookPostLike.setCerebookPost(cerebookPostLike.getCerebookPost());
            cerebookPostLike.setCountLike(countLike);
            postLikeRepository.updateLike(id,countLike,liked);

        } else {
            throw new postNotFoundException(String.format("cet like na pas été trouvé !"+id));

        }
        return "redirect:/allPosts";
    }
}


