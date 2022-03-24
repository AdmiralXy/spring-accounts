package com.admiralxy.springaccounts.service;

import com.admiralxy.springaccounts.entity.Credential;
import com.admiralxy.springaccounts.entity.User;
import com.admiralxy.springaccounts.service.interfaces.ICredentialService;
import com.admiralxy.springaccounts.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-tests.properties")
@Transactional
@WithMockUser("someUser")
class CredentialServiceTest {

    @Autowired
    private ICredentialService credentialService;

    @Autowired
    private IUserService userService;

    private final static String SECURITY_KEY = "securityKey";
    private Credential credential;
    private User user;

    @BeforeEach
    void setUp() {
        user = userService.save(new User("someUser", "somePassword"));
        credential = new Credential("Face App", "Some Login", "somePassword", SECURITY_KEY, user);
    }

    @Test
    void getAll() throws Exception {
        Credential credential1 = new Credential("Face App", "Some Login", "somePassword", SECURITY_KEY, user);
        Credential credential2 = new Credential("Come App", "Some Login", "somePassword", SECURITY_KEY, user);
        credentialService.save(credential1);
        credentialService.save(credential2);
        List<Credential> credentialList = credentialService.getAll();
        assertThat(credentialList).contains(credential1, credential2);
    }

    @Test
    void findById() throws Exception {
        credentialService.save(credential);
        Credential credentialFromDb = credentialService.findById(credential.getId(), SECURITY_KEY);
        assertEquals(credential.getId(), credentialFromDb.getId());
        assertEquals(credential.getName(), credentialFromDb.getName());
    }

    @Test
    void delete() throws Exception {
        credentialService.save(credential);
        credentialService.delete(credential.getId());
        Credential credentialServiceById = credentialService.findById(credential.getId(), SECURITY_KEY);
        assertNull(credentialServiceById);
    }

    @Test
    void save() throws Exception {
        Credential createdCredential = credentialService.save(credential);
        assertNotNull(createdCredential);
    }

    @Test
    void getAllByUser() throws Exception {
        Credential credential1 = new Credential("Face App", "Some Login", "somePassword", SECURITY_KEY, user);
        Credential credential2 = new Credential("Come App", "Some Login", "somePassword", SECURITY_KEY, user);
        credentialService.save(credential1);
        credentialService.save(credential2);
        List<Credential> credentialList = credentialService.getAll();
        assertThat(credentialList).contains(credential1, credential2);
        assertThat(credentialList.get(0).getUser()).isEqualTo(user);
        assertThat(credentialList.get(1).getUser()).isEqualTo(user);
    }
}