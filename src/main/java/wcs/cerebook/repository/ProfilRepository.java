package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookMessage;
import wcs.cerebook.entity.CerebookProfil;
import wcs.cerebook.entity.CerebookUser;

import java.util.List;

@Repository
public interface ProfilRepository extends JpaRepository<CerebookProfil, Long> {
}
