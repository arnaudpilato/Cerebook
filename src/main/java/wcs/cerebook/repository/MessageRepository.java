package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookMessage;
import wcs.cerebook.entity.CerebookUser;

@Repository
public interface MessageRepository extends JpaRepository<CerebookMessage, Long> {
    @Query("SELECT m FROM CerebookMessage m WHERE m.currentUser = :currentUser")
   public CerebookMessage getCerebookMessageByCurrentUser_Id(@Param("currentUser") Long currentUser);

}
