package am.itspace.restfulwebservices.controller;

import am.itspace.restfulwebservices.exception.UserNotFoundException;
import am.itspace.restfulwebservices.model.User;
import am.itspace.restfulwebservices.service.dao.UserDaoService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {

    private UserDaoService userService;

    UserController(UserDaoService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity saveUser(@Valid @RequestBody User user){
        User savedUser = userService.saveUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}")
    public Resource<User> getUser(@PathVariable int id){
        User userById = userService.getUserById(id);
        if (userById==null){
            throw new UserNotFoundException("id - " + id);
        }
        Resource<User> resource = new Resource<>(userById);
        ControllerLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(link.withRel("All Users"));

        return resource;
    }

    @DeleteMapping("/users/{id}")
    public User deleteUserById(@PathVariable int id){
        User user = userService.deleteUserById(id);
        if (user == null){
            throw new UserNotFoundException("id - " + id);
        }
        return user;
    }

}
