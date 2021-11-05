package wcs.cerebook.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookPost;

@Repository
public interface PostRepository extends CrudRepository<CerebookPost, Long> {
}
