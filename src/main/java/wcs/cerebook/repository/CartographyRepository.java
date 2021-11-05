package wcs.cerebook.repository;

import org.springframework.data.repository.CrudRepository;
import wcs.cerebook.entity.CerebookCartography;

public interface CartographyRepository extends CrudRepository<CerebookCartography, Long> {
}
