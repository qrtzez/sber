package com.sber.sber;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class SberApplicationTests {

    @Autowired(required=false)
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

}
