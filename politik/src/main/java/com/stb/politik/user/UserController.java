package com.stb.politik.user;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stb.politik.credentials.Credentials;
import com.stb.politik.credentials.CredentialsService;
import com.stb.politik.dto.AuthenticationResponse;
import com.stb.politik.dto.LoginRequest;
import com.stb.politik.dto.UserDTO;
import com.stb.politik.security.JwtUtils;

@RestController
@RequestMapping("api/users")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/phone")
    public User getUserByPhone(@RequestBody String phone) {
        return userService.getUserByPhone(phone);
    }

    @PostMapping("/email")
    public User getUserByEmail(@RequestBody String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/username")
    public User getUserByUsername(@RequestBody String username) {
        System.out.println("UserController.getUserByUsername - created_at" + this.userService.getUserByUsername(username).getCreatedAt());
        return this.userService.getUserByUsername(username);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        User user = new User(); // creo el user
        user.setUsername(userDTO.getUsername());
        user.setRol(userDTO.getRol());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        // esto es para que si el campo está vacío, no se guarde en la db, ya que si
        // está vacío es '' y sería duplicado
        user.setPhone(userDTO.getPhone().isEmpty() ? null : userDTO.getPhone());
        user.setEmail(userDTO.getEmail().isEmpty() ? null : userDTO.getEmail());
        user.setBirthday(userDTO.getBirthday());
        // Configurar la fecha y hora actual
        LocalDateTime currentDateTime = LocalDateTime.now();
        user.setCreatedAt(currentDateTime); // Establecer la fecha y hora actual en el usuario

        // guardo el user en la db
        this.userService.saveUser(user);

        // //Ahora he de hashear la password
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        // creo las credenciales y las seteo
        Credentials credentials = new Credentials();
        // como el user ya tiene id (pq ha sido saved en la db), se lo seteo a las
        // credenciales
        credentials.setUser(user);
        credentials.setPasswordHash(hashedPassword);
        // guardo las credenciales en la db
        this.credentialsService.saveCredentials(credentials);

        // Generar un token de autenticación
        String jwt = JwtUtils.generateToken(user.getUsername()); // modifieeeed 090121---------------------

        // Devolver el token al cliente
        return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String password = loginRequest.getPassword();
        // en funcion de username:
        String username = loginRequest.getUsername();
        // int index = loginRequest.getIndex(); //en teoria ya no hace falta

        User user = null;
        user = this.userService.getUserByUsername(username); // busco el user con este username
        if (user == null) {
            log.info("login() - Backend - No existe un usuario con ese username.");
            return new ResponseEntity<>("Invalid username", HttpStatus.UNAUTHORIZED);
        }

        // obtengo las credentials de dicho user
        Credentials credentials = this.credentialsService.getCredentialsByUser(user);
        if (credentials == null) {
            log.info("login() - Backend - No credentials found for user");
            return new ResponseEntity<>("No credentials found for user", HttpStatus.UNAUTHORIZED);
        }
        // obtengo la password de dicho username
        String pwHash = credentials.getPasswordHash();
        log.info("credentials - pwHash del user:" + user.getUsername() + " = " + pwHash);

        // Verificar la contraseña (introducida vs la de dicho user)
        boolean passwordMatches = passwordEncoder.matches(password, pwHash);
        if (!passwordMatches) {
            log.info("login() - Backend - contraseña incorrecta.");
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        } else {
            log.info("login() - Backend - contraseña correcta.");

            // Generar un token JWT
            String jwt = JwtUtils.generateToken(user.getUsername()); // modifieeeed 090221 ---------------------
            log.info("login() - Backend - jwt generado: " + jwt);

            // Devolver el token al cliente
            return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
        }
    }
}