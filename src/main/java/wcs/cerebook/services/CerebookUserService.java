package wcs.cerebook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.model.MyUserDetails;
import wcs.cerebook.repository.UserRepository;

import java.util.List;

@Service
public class CerebookUserService {
    @Autowired
    private UserRepository repo;

    public List<CerebookUser> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

}
