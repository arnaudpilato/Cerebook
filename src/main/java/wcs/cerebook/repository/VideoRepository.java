package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookPicture;
import wcs.cerebook.entity.CerebookVideo;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<CerebookVideo, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM cerebook_video v WHERE v.user_id = :userId ORDER BY id DESC LIMIT 6")
    List<CerebookVideo> lastVideo(@Param("userId") Long userId);
}
