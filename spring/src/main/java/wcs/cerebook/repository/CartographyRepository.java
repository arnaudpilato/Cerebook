package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wcs.cerebook.entity.CerebookCartography;

public interface CartographyRepository extends JpaRepository<CerebookCartography, Long> {
}
