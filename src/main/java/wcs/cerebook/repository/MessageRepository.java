package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookMessage;

@Repository
public interface MessageRepository extends JpaRepository<CerebookMessage, Long> {
}
