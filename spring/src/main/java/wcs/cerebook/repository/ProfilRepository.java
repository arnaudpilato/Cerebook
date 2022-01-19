package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookProfil;

@Repository
public interface ProfilRepository extends JpaRepository<CerebookProfil, Long> {
}
