package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    static List<User> userList = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getUserList() {
        return userList;
    }

    @PostMapping("/users")
    public User postUser(@RequestBody UserRequest userData) {
        UUID id = UUID.randomUUID();
        String name = userData.userName();
        String email = userData.userEmail();
        User user = new User(id, name, email);

        userList.add(user);


        return user;
    }

    @GetMapping("/users/{id}")
    public UserRequest getUserById(@PathVariable UUID id) {

        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(user -> new UserRequest(user.getName(), user.getEmail()))
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));
    }

}
