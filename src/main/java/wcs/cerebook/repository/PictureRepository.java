package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookPicture;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<CerebookPicture, Long> {
    //@Query(nativeQuery = true, value = "SELECT id FROM CerebookPicture p WHERE p.user_id = :userId ORDER BY id DESC LIMIT 6")
    //List<CerebookPicture> lastPicture(@Param("userId") Long userId);
}
