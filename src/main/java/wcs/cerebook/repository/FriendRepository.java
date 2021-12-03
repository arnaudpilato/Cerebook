package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookConfirmationFriend;
import wcs.cerebook.entity.CerebookFriend;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.entity.CerebookVideo;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<CerebookFriend, Long> {
    @Query("SELECT f FROM CerebookFriend f WHERE f.confirmationFriend = :confirmationFriend")
    public CerebookFriend getByNotConfirmationFriend_Id(@Param("confirmationFriend") CerebookConfirmationFriend confirmationFriend);

    @Query("SELECT f FROM CerebookFriend f WHERE f.currentUser = :currentUser AND f.confirmationFriend.add = true ")
    public List<CerebookFriend> getByConfirmationFriend_Id(@Param("currentUser") CerebookUser currentUser);

    @Query("SELECT f FROM CerebookFriend f WHERE f.currentFriends = :currentFriends AND f.confirmationFriend.add = true ")
    public List<CerebookFriend> getByConfirmationFriendUser_Id(@Param("currentFriends") CerebookUser currentFriends);

    @Query(nativeQuery = true, value = "SELECT * FROM cerebook_friend f WHERE f.current_user_id = :userId ORDER BY id DESC LIMIT 6")
    List<CerebookFriend> lastfriends(@Param("userId") Long userId);
}
