package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookRole;

@Repository
public interface RoleRepository extends JpaRepository<CerebookRole, Long> {
}
