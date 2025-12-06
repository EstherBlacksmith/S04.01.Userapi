import entities.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository {
    static List<User> userList = new ArrayList<>();

    @Override
    public User save(User user) {
        userList.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(userList);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> searchByName(String name) {
        return userList.stream()
                .filter(user -> user.getName().contains(name) ).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userList.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
