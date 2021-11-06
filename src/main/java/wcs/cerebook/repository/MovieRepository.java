package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookMovie;

@Repository
public interface MovieRepository extends JpaRepository<CerebookMovie, Long> {
}
