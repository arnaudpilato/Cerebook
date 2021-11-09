package wcs.cerebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookUser;

@Repository
public interface UserRepository extends JpaRepository<CerebookUser, Long> {
    CerebookUser findByUsername(String username);

    @Query("SELECT u FROM CerebookUser u WHERE u.username = :username")
    public CerebookUser getCerebookUserByUsername(@Param("username") String username);



}
