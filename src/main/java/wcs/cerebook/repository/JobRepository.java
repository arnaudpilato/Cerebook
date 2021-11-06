package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookJob;

@Repository
public interface JobRepository extends JpaRepository<CerebookJob, Long> {
}
