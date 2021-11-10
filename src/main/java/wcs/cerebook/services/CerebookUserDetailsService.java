package wcs.cerebook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.model.MyUserDetails;
import wcs.cerebook.repository.UserRepository;

@Service
public class CerebookUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository cerebookUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        CerebookUser user = cerebookUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("could not find user");
        }
        return new MyUserDetails(user);
    }
    public Long getUserId(){
        return cerebookUserRepository.
    }
}
