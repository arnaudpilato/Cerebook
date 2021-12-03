package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookMessage;
import wcs.cerebook.entity.CerebookUser;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<CerebookMessage, Long> {
    @Query("SELECT m FROM CerebookMessage m JOIN CerebookUser c ON c IN (SELECT d FROM m.userDestination d) WHERE" +
            " m.currentUser = :currentUser AND c = :currentDest")
    public List<CerebookMessage> getCerebookMessageByCurrentUseraAndUserDestination(
            @Param("currentUser") CerebookUser currentUser,
            @Param("currentDest") CerebookUser currentDest);

    // PIL: Requête pour récupérer les 3 derniers messages reçu sur la page profil
    @Query(nativeQuery = true, value = "SELECT min(m.id) as max_m_id, m.current_user_id " +
            "FROM cerebook_message m " +
            "JOIN cerebook_message_user_destination cmud ON m.id = cmud.cerebook_message_id " +
            "JOIN cerebook_user c ON c.id = cmud.user_destination_id " +
            "WHERE c.id = :current_user_id " +
            "GROUP BY m.current_user_id " +
            "ORDER BY max_m_id DESC LIMIT 3")
    List<Long[]> lastThreeMessages(
            @Param("current_user_id") Long user_id);

}
