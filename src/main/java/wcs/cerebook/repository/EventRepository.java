package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookEvent;

@Repository
public interface EventRepository extends JpaRepository<CerebookEvent, Long> {
}
