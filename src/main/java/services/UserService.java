package services;

import entities.Email;
import entities.Name;
import entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById();
    User getUserByName();
    User getUserByEmail();
    Name getUserName(User user);
    Email getUserEmail(User user);
    UUID getUserId(User user);

}
