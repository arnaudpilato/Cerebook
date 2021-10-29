package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wcs.cerebook.entity.User;
import wcs.cerebook.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/create")
    public User createUser(String name) {
        User user = new User(name);

        return userRepository.save(user);
    }

    @RequestMapping("/read")
    public User getUser(Integer userId) {
        return userRepository.findById(userId).get();
    }

    @RequestMapping("/readall")
    public List <User> getAllUsers() {
        List<User> cerebookPost = new ArrayList<>();
        userRepository.findAll().forEach(cerebookPost::add);
        return cerebookPost;
    }

    @RequestMapping("/update")
    public User updateUser(Integer userId, String name) {
        User userToUpdate = userRepository.findById(userId).get();
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