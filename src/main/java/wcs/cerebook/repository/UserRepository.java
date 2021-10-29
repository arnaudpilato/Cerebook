package wcs.cerebook.repository;


import org.springframework.data.repository.CrudRepository;
import wcs.cerebook.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
