package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookVideo;

@Repository
public interface VideoRepository extends JpaRepository<CerebookVideo, Long> {
}
