package com.admiralxy.springaccounts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application-tests.properties")
class SpringAccountsApplicationTests {

    @Test
    void contextLoads() {
    }

}
