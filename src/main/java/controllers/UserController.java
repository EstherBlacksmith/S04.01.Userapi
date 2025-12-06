package controllers;

import entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
public class UserController {
    static List<User> userList = new ArrayList<>();

    public static List<User> getUserList() {
        return userList;
    }

    @GetMapping("/users")
    public Object getUserList(@RequestParam(required = false) String userName) {

        if (userName == null) {
            return userList;
        }
        return userList.stream()
                .filter(user -> user.getName().contains(userName.toLowerCase(Locale.ROOT)))
                .findFirst()
                .map(user -> new UserRequest(user.getName(), user.getEmail()))
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping("/users")
    public User postUser(@RequestBody UserRequest userData) {
        UUID id = UUID.randomUUID();
        String name = userData.userName();
        String email = userData.userEmail();

        User user = new User( name, email);

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
