package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.entities.User;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public User postUser (@RequestBody UserRequest userData) {
        UUID id = UUID.randomUUID();
        String name = userData.userName();
        String email = userData.userEmail();

        User user = new User(id,name,email);

        userList.add(user);

        return user;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UserRequest getUserById(@PathVariable UUID id) {
        UserRequest userRequest = null;
        if (userList.contains(id)) {
            userList.stream()
                    .filter(user -> user.getId().compareTo(id) > 0)
                    .;
        }
        return userRequest;
    }

}
