package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/create")
    public CerebookUser createUser(String nickName, String firstName, String lastName, String city, String address, String email, String password, Date birthday) {
        CerebookUser user = new CerebookUser(nickName, firstName, lastName, city, address, email, password, birthday);
        return userRepository.save(user);
    }

    @RequestMapping("/read")
    public CerebookUser getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @RequestMapping("/readall")
    public List <CerebookUser> getAllUsers() {
        List<CerebookUser> cerebookPost = new ArrayList<>();
        userRepository.findAll().forEach(cerebookPost::add);
        return cerebookPost;
    }

    @RequestMapping("/update")
    public CerebookUser updateUser(Long userId, String nickName, String firstName, String lastName, String city, String address, String email, String password, Date birthday) {
        CerebookUser userToUpdate = userRepository.findById(userId).get();
        if (userId != null) {
            userToUpdate.setNickName(nickName);
            userToUpdate.setFirstName(firstName);
            userToUpdate.setLastName(lastName);
            userToUpdate.setCity(city);
            userToUpdate.setAddress(address);
            userToUpdate.setPassword(password);
            userToUpdate.setBirthday(birthday);
        }
        return userRepository.save(userToUpdate);
    }
    @RequestMapping("/delete")
    public void deleteUser(Long userId) {
       userRepository.deleteById(userId);
    }
}