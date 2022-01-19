package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookPost;

@Repository
public interface PostRepository extends JpaRepository<CerebookPost, Long> {

}
