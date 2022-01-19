package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wcs.cerebook.entity.CerebookConfirmationFriend;
import wcs.cerebook.entity.CerebookUser;

import java.util.List;

public interface ConfirmRepository extends JpaRepository<CerebookConfirmationFriend, Long> {
    @Query("SELECT c FROM CerebookConfirmationFriend c WHERE c.friendUser = :user AND " +
            "c.add = false ")
    public List<CerebookConfirmationFriend> getByUserFriendId(@Param("user") CerebookUser user);
}
