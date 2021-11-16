package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookComment;
import wcs.cerebook.entity.CerebookUser;

@Repository
public interface CommentRepository extends JpaRepository<CerebookComment, Long> {

}
