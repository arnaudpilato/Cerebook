package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wcs.cerebook.entity.CerebookComment;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.entity.CerebookPostLike;
import wcs.cerebook.entity.CerebookUser;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<CerebookPostLike, Long> {

    @Query("SELECT pl FROM CerebookPostLike pl  WHERE pl.cerebookUser = :cerebookUser")
    public List<CerebookPostLike> cerebookLikeByUserId(@Param("cerebookUser") CerebookUser cerebookUser);
    @Query("SELECT COUNT(pl) FROM CerebookPostLike pl WHERE  pl.cerebookPost.id = :countpost")
    public Long  countCerebookLikeByPostId(@Param("countpost") Long postid);
    @Query("SELECT pl FROM CerebookPostLike pl WHERE  pl.cerebookUser.id = :cerebookUser")
    public Long  CerebookUserByLikeId(@Param("cerebookUser") CerebookPostLike cerebookPostLike);

}
