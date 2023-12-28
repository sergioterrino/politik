package com.stb.politik.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stb.politik.credentials.Credentials;
import com.stb.politik.credentials.CredentialsService;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users/phone")
    public User getUserByPhone(@RequestBody String phone) {
        return userService.getUserByPhone(phone);
    }

    @PostMapping("/users/email")
    public User getUserByEmail(@RequestBody String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/users/username")
    public User getUserByUsername(@RequestBody String username) {
        return this.userService.getUserByUsername(username);
    }

    @PostMapping("/users/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {

        // creo el user
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        //esto es para que si el campo está vacío, no se guarde en la db, ya que si está vacío es '' y sería duplicado
        user.setPhone(userDTO.getPhone().isEmpty() ? null : userDTO.getPhone());
        user.setEmail(userDTO.getEmail().isEmpty() ? null : userDTO.getEmail());
        user.setBirthday(userDTO.getBirthday());
        // guardo el user en la db
        this.userService.saveUser(user);

        // //Ahora he de hashear la password
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        //creo las credenciales y las seteo
        Credentials credentials = new Credentials();
        credentials.setUser(user); //como el user ya tiene id (pq ha sido saved en la db), se lo seteo a las credenciales
        credentials.setPasswordHash(hashedPassword);
        //guardo las credenciales en la db
        this.credentialsService.saveCredentials(credentials);

        // El método ok() es un atajo para construir una ResponseEntity con el código de estado 200 OK.
        // El método build() se utiliza para construir la ResponseEntity.
        return ResponseEntity.ok().build();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long
    // userId) throws UserNotFoundException {
    // User user = userRepository.findById(userId)
    // .orElseThrow(() -> new UserNotFoundException("User not found for this id :: "
    // + userId));
    // return ResponseEntity.ok().body(user);
    // }

}