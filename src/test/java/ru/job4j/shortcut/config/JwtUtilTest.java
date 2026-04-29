package ru.job4j.shortcut.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtUtilTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret",
                "12345678901234567890123456789012");
        ReflectionTestUtils.setField(jwtUtil, "expiration",
                1000000L);
    }

    @Test
    void whenGenerateTokenThenValid() {
        String token = jwtUtil.generateToken("user");
        Assertions.assertTrue(jwtUtil.validate(token));
        Assertions.assertEquals("user", jwtUtil.getLogin(token));
    }
}