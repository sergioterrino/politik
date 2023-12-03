package com.stb.politik.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws UserNotFoundException {
    //     User user = userRepository.findById(userId)
    //             .orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));
    //     return ResponseEntity.ok().body(user);
    // }

    // @PostMapping
    // public User createUser(@Valid @RequestBody User user) {
    //     return userRepository.save(user);
    // }
}
