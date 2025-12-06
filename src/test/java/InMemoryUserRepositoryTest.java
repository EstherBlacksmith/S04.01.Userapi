import entities.Email;
import entities.Name;
import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class InMemoryUserRepositoryTest {
    User user1;
    InMemoryUserRepository inMemoryUserRepository;

    @BeforeAll
    void initializingVariablesValues() {
        this.user1 = new User(new Name("Pepito"), new Email("pepito@gmail.com"));
        User user2 = new User(new Name("Pepita"),new Email("pepita@gmail.com"));
        inMemoryUserRepository = new InMemoryUserRepository();
        inMemoryUserRepository.save(user1);
        inMemoryUserRepository.save(user2);
    }

    @Test
    void save() {
        User user3 = inMemoryUserRepository.save(user1);
        assertEquals(user3, user1);
    }

    @Test
    void findAll() {
        assertTrue(inMemoryUserRepository.findAll().contains(user1));
    }

    @Test
    void findById() {
        Optional<User> user3 = inMemoryUserRepository.findById( user1.getId());
        assertTrue(user3.isPresent());

        assertEquals(user1, user3.get());
    }

    @Test
    void searchByName() {
        List<User> usersByName = inMemoryUserRepository.searchByName( user1.getName());

        assertThat(usersByName.contains(user1));
    }

    @Test
    void existsByEmail() {
        inMemoryUserRepository.existsByEmail( user1.getEmail());

        assertTrue( inMemoryUserRepository.existsByEmail( user1.getEmail()));
        assertFalse(inMemoryUserRepository.existsByEmail( user1.getEmail() + "alallalala"));
    }
}