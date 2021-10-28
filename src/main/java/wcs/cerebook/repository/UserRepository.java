package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
}
