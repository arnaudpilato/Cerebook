package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookComment;

@Repository
public interface CommentRepository extends JpaRepository<CerebookComment, Long> {
}
