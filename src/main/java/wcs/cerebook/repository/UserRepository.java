package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookUser;

@Repository
public interface UserRepository extends JpaRepository<CerebookUser, Long> {

}
