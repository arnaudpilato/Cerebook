package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookPicture;

@Repository
public interface PictureRepository extends JpaRepository<CerebookPicture, Long> {
}
