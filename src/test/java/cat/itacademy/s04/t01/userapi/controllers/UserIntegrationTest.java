package cat.itacademy.s04.t01.userapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        UserController.getUserList().clear();
    }

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(new UserRequest("Ada", "ada@lovalace.com"));

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ada"))
                .andExpect(jsonPath("$.email").value("ada@lovalace.com"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void getUserById_returnsCorrectUser() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(new UserRequest("Joan", "Joan@Clarke.com"));

        String postResponse = mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, String> responseMap = objectMapper.readValue(postResponse, Map.class);
        String userId = responseMap.get("id");

        mockMvc.perform(get("/users/{id}",userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Joan"))
                .andExpect(jsonPath("$.userEmail").value("Joan@Clarke.com"));

    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {
        UUID userId =  UUID.randomUUID();

        mockMvc.perform(get("/users/{id}",userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {
        UserRequest jsonRequest1 = new UserRequest("Joan", "Joan@Clarke.com");
        UserRequest jsonRequest2 = new UserRequest("Ada", "ada@lovalace.com");

        List<UserRequest> usersToAdd = List.of(jsonRequest1,jsonRequest2);

        for (UserRequest user : usersToAdd){
            mockMvc.perform(post("/users")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(user)));
        }

        mockMvc.perform(get("/users")
                .param("name","jo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",containsStringIgnoringCase("jo")));

    }
}