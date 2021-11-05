package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookFriend;

@Repository
public interface FriendRepository extends JpaRepository<CerebookFriend, Long> {
}
