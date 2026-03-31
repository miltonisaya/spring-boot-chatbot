package com.watabelabs.chatbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ChatbotApplicationTests {

	@MockitoBean
	JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

}
