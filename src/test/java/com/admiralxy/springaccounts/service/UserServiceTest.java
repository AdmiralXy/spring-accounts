package com.admiralxy.springaccounts.service;

import com.admiralxy.springaccounts.entity.Role;
import com.admiralxy.springaccounts.entity.User;
import com.admiralxy.springaccounts.service.interfaces.IUserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-tests.properties")
@Transactional
class UserServiceTest {

    @Autowired
    private IUserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("someUser", "somePassword");
    }

    @Test
    void getAll() {
        User user1 = new User("someUser1", "somePassword");
        User user2 = new User("someUser2", "somePassword");
        userService.save(user1);
        userService.save(user2);
        List<User> productList = userService.getAll();
        assertThat(productList).contains(user1, user2);
    }

    @Test
    void findByUsername() {
        User user = userService.save(this.user);
        User userFromDb = userService.findByUsername(user.getUsername());
        assertEquals(user.getId(), userFromDb.getId());
        assertEquals(user.getUsername(), userFromDb.getUsername());
    }

    @Test
    void findById() {
        User user = userService.save(this.user);
        User userFromDb = userService.findById(user.getId());
        assertEquals(user.getId(), userFromDb.getId());
        assertEquals(user.getUsername(), userFromDb.getUsername());
    }

    @Test
    void delete() {
        userService.save(user);
        userService.delete(user.getId());
        User userServiceById = userService.findById(user.getId());
        assertNull(userServiceById);
    }

    @Test
    void save() {
        User createdUser = userService.save(user);
        assertNotNull(createdUser);
        assertTrue(CoreMatchers.is(createdUser.getRoles()).matches(Collections.singleton(Role.USER)));
    }

    @Test
    void saveFail() {
        User createdUser = userService.save(user);
        assertNotNull(createdUser);
        User anotherCreatedUser = userService.save(user);
        assertNull(anotherCreatedUser);
    }
}