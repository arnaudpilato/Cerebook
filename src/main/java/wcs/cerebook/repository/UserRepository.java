package wcs.cerebook.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wcs.cerebook.entity.CerebookUser;

@Repository
public interface UserRepository extends CrudRepository<CerebookUser, Integer> {
    CerebookUser findByName(String name);
}
