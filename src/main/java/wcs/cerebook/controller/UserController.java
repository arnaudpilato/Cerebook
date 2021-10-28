package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wcs.cerebook.entity.User;
import wcs.cerebook.repository.UserRepository;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/create")
    public User createUser(String name) {
        User user = new User(name);

        return userRepository.save(user);
    }

    @RequestMapping("/read")
    public User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @RequestMapping("/readall")
    public List <User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("/update")
    public User updateUser(Long userId, String name) {
        User userToUpdate = userRepository.findById(userId).get();
        if (name != null) {
            userToUpdate.setName(name);
        }

        return userRepository.save(userToUpdate);
    }

    @RequestMapping("/delete")
    public void deleteUser(Long userId) {
       userRepository.deleteById(userId);
    }
}