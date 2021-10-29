package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/create")
    public CerebookUser createUser(String name) {
        CerebookUser user = new CerebookUser(name);

        return userRepository.save(user);
    }

    @RequestMapping("/read")
    public CerebookUser getUser(Integer userId) {
        return userRepository.findById(userId).get();
    }

    @RequestMapping("/readall")
    public List <CerebookUser> getAllUsers() {
        List<CerebookUser> cerebookPost = new ArrayList<>();
        userRepository.findAll().forEach(cerebookPost::add);
        return cerebookPost;
    }

    @RequestMapping("/update")
    public CerebookUser updateUser(Integer userId, String name) {
        CerebookUser userToUpdate = userRepository.findById(userId).get();
        if (name != null) {
            userToUpdate.setName(name);
        }

        return userRepository.save(userToUpdate);
    }

    @RequestMapping("/delete")
    public void deleteUser(Integer userId) {
       userRepository.deleteById(userId);
    }
}