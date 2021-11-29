package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookPost;

@Repository
public interface PostRepository extends JpaRepository<CerebookPost, Long> {
    @Modifying
    @Query("update CerebookPost p set p.countLike =:countLike , p.liked =:liked where p.id = :id")
    void updateLike(@Param(value = "id") long id, @Param(value = "countLike") long countLike,@Param(value = "liked") boolean liked);
}
