package am.itspace.restfulwebservices.controller;

import am.itspace.restfulwebservices.exception.PostNotFoundException;
import am.itspace.restfulwebservices.exception.UserNotFoundException;
import am.itspace.restfulwebservices.model.Post;
import am.itspace.restfulwebservices.model.User;
import am.itspace.restfulwebservices.repository.PostRepository;
import am.itspace.restfulwebservices.repository.UserRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/jpa/users")
    public ResponseEntity saveUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/jpa/users/{id}")
    public Resource<User> getUser(@PathVariable int id){
        Optional<User> userById = userRepository.findById(id);
        if (!userById.isPresent()){
            throw new UserNotFoundException("id - " + id);
        }
        Resource<User> resource = new Resource<>(userById.get());
        ControllerLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(link.withRel("All Users"));

        return resource;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> getAllPostsByUserId(@PathVariable int id){
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()){
            throw new UserNotFoundException("id - " + id);
        }
        return byId.get().getPosts();
    }

    @GetMapping("/jpa/users/{userId}/posts/{postId}")
    public Resource<Post> getPostByUserId(@PathVariable int userId,@PathVariable int postId){
        Optional<User> byId = userRepository.findById(userId);
        if (!byId.isPresent()){
            throw new UserNotFoundException("id - " + userId);
        }
        Optional<Post> postById = postRepository.findById(postId);
        if (!postById.isPresent()){
            throw new PostNotFoundException("id - " + postId);
        }

        ControllerLinkBuilder link = linkTo(methodOn(this.getClass()).getPostByUserId(userId, postId));
        Resource<Post> postResource = new Resource<>(postById.get());
        postResource.add(link.withSelfRel());
        return postResource;
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity savePost(@PathVariable int id,@Valid @RequestBody Post post){
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()){
            throw new UserNotFoundException("id - " + id);
        }
        post.setUser(byId.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
