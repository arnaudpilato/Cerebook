package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wcs.cerebook.entity.CerebookConfirmationFriend;

public interface ConfirmRepository extends JpaRepository<CerebookConfirmationFriend, Long> {
}
