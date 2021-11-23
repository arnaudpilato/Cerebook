package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookPost;

import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<CerebookPost, Long> {
@Query("UPDATE CerebookPost l set l.countLike = l.countLike + 1 WHERE l.id = :id")
    public void incrementLikeCount();
@Query("UPDATE CerebookPost l set l.countLike = l.countLike - 1 WHERE l.id = :id")
    public void decrementLikeCount();
}
