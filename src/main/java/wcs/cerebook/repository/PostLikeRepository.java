package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wcs.cerebook.entity.CerebookComment;
import wcs.cerebook.entity.CerebookPostLike;

@Repository
public interface PostLikeRepository extends JpaRepository<CerebookPostLike, Long> {
    /*@Transactional
    @Modifying
    @Query("update CerebookPostLike p set p.countLike =:countLike , p.liked =:liked where p.id = :id")
    void updateLike(@Param(value = "id") long id, @Param(value = "countLike") long countLike, @Param(value = "liked") boolean liked);*/
}
