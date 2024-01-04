package com.stb.politik.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.stb.politik.utils.JwtUtils;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtil;

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
        // esto es para que si el campo está vacío, no se guarde en la db, ya que si
        // está vacío es '' y sería duplicado
        user.setPhone(userDTO.getPhone().isEmpty() ? null : userDTO.getPhone());
        user.setEmail(userDTO.getEmail().isEmpty() ? null : userDTO.getEmail());
        user.setBirthday(userDTO.getBirthday());
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
        String jwt = jwtUtil.generateToken(user);

        // Devolver el token al cliente
        return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        String password = loginRequest.getPassword();
        // en funcion de namePhoneEmail:
        String namePhoneEmail = loginRequest.getNamePhoneEmail();
        System.out.println("userContr - login() - namePhoneEmail = " + namePhoneEmail);
        int index = loginRequest.getIndex();
        System.out.println("index fuera = " + index);

        User user = null;
        if (index == 1) { // index==1 es que el usuario ha metido un username
            // busco el user con este username
            user = this.userService.getUserByUsername(namePhoneEmail);
            System.out.println("Entrando en index = " + index);
            if (user == null) {
                return new ResponseEntity<>("Invalid username", HttpStatus.UNAUTHORIZED);
            }
        } else if (index == 2) {
            // busco el user con este phone
            user = this.userService.getUserByPhone(namePhoneEmail);
            System.out.println("user index = 2 -> " + user);
            System.out.println("Entrando en index = " + index);
            if (user == null) {
                return new ResponseEntity<>("Invalid phone", HttpStatus.UNAUTHORIZED);
            }
        } else if (index == 3) {
            // busco el user con este email
            user = this.userService.getUserByEmail(namePhoneEmail);
            System.out.println("Entrando en index = " + index);
            if (user == null) {
                return new ResponseEntity<>("Invalid email", HttpStatus.UNAUTHORIZED);
            }
        }

        // obtengo las credentials de dicho user
        Credentials credentials = this.credentialsService.getCredentialsByUser(user);
        if (credentials == null) {
            return new ResponseEntity<>("No credentials found for user", HttpStatus.UNAUTHORIZED);
        }
        // obtengo la password de dicho username
        String pwHash = credentials.getPasswordHash();
        System.out.println("credentials - pwHash del user = " + pwHash);

        // Verificar la contraseña (introducida vs la de dicho user)
        boolean passwordMatches = passwordEncoder.matches(password, pwHash);
        if (!passwordMatches) {
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        } else {
            System.out.println("login() - Backend - contraseña correcta.");

            // Generar un token JWT
            String jwt = jwtUtil.generateToken(user);

            // Devolver el token al cliente
            return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
        }

    }

}