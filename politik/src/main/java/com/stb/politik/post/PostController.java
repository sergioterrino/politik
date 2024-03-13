package com.stb.politik.post;

import org.springframework.web.bind.annotation.RestController;

import com.stb.politik.dto.PostDTO;
import com.stb.politik.user.User;
import com.stb.politik.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static Logger log = LoggerFactory.getLogger(PostController.class.getName());

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<Post> getPosts() {
        return this.postService.getPosts();
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable Long userId) {
        // Obtengo el objeto Authentication deSpring Security
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // log.info("PostController - getAllPostsByUser() - Authentication -> " +
        // authentication.toString());
        // // Verifico si el usuario está autenticado
        // if (authentication.isAuthenticated() && !(authentication instanceof
        // AnonymousAuthenticationToken)) {
        // // Obtengo el nombre de usuario del objeto Authentication
        // String username = authentication.getName();
        // log.info("PostController - getAllPostsByUser() - Username auth -> " +
        // username);

        // Busca al usuario en tu base de datos
        User user = userService.getUserById(userId);
        // Verifico si el usuario existe en la base de datos
        if (user != null) {
            List<Post> posts = postService.getPostsByUserId(userId);
            log.info("PostController - getAllPostsByUser() - Posts -> " + posts.toString());
            return posts;
            // return new ResponseEntity<>(posts, HttpStatus.OK);
        } else {
            // Lanzo una excepción si el usuario no se encuentra en la base de datos
            throw new UsernameNotFoundException("User not found");
        }
        // } else {
        // log.info("User not authenticated----------------");
        // // Lanzo una excepción si el usuario no está autenticado
        // throw new AuthenticationCredentialsNotFoundException("User not
        // authenticated");
        // }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('politik')")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {

        Post post = new Post();
        post.setText(postDTO.getText());
        post.setImagePath(postDTO.getImagePath());
        post.setVideoPath(postDTO.getVideoPath());
        // establezco la hora
        LocalDateTime currentDate = LocalDateTime.now();
        post.setCreatedAt(currentDate);
        log.info("PostController - createPost() - PostDTO recibido: - post.toString() -> " + post.toString());

        // ahora necesito obtener el user_id del user autenticado
        // Obtengo el objeto Authentication deSpring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("PostController - createPost() - Authentication -> " + authentication.toString());
        // Verifico si el usuario está autenticado
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            // Obtengo el nombre de usuario del objeto Authentication
            String username = authentication.getName();
            log.info("PostController - createPost() - Username auth -> " + username);

            // Busca al usuario en tu base de datos
            Optional<User> optionalUser = userService.getUserByUsername(username);

            // Verifico si el usuario existe en la base de datos
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                post.setUser(user);
                post.setName(user.getName());
                post.setLastname(user.getLastname());
                post.setUsername(user.getUsername());
                log.info("PostController - createPost() - Post antes de enviarse" + post.toString());

                // guardo el post en la db
                postService.savePost(post);

                return ResponseEntity.ok(post);
            } else {
                // Lanzo una excepción si el usuario no se encuentra en la base de datos
                throw new UsernameNotFoundException("User not found");
            }
        } else {
            log.info("User not authenticated----------------");
            // Lanzo una excepción si el usuario no está autenticado
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
        }
    }

    // @PostMapping("/posts/create")
    // public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {

    // log.info("PostDTO recibido: ");
    // Post post = new Post();
    // post.setText(postDTO.getText());
    // post.setImagePath(postDTO.getImagePath());
    // post.setVideoPath(postDTO.getVideoPath());
    // // establezco la hora
    // LocalDateTime currentDate = LocalDateTime.now();
    // post.setCreatedAt(currentDate);

    // // ahora necesito obtener el user_id del user autenticado
    // // Obtengo el objeto Authentication deSpring Security
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();

    // // Obtengo el nombre de usuario del objeto Authentication
    // String username = authentication.getName();
    // log.info("Username auth -> " + username);

    // // Busca al usuario en tu base de datos
    // User user = userService.getUserByUsername(username);

    // post.setUser(user);
    // log.info("Post antes de enviarse" + post.toString());

    // // guardo el post en la db
    // postService.savePost(post);

    // return ResponseEntity.ok(post);
    // }
}