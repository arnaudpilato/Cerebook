package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookMovie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<CerebookMovie, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM cerebook_movie AS m JOIN cerebook_user_user_movies AS cma ON m.id = cma.user_movies_id WHERE cma.user_movies_id = :user_id ORDER BY id DESC LIMIT 6")
    List<CerebookMovie> lastMovie(@Param("user_id") Long user_id);
}
