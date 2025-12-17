package com.intrawise;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntraWiseApplicationTests {

	@Test
	void contextLoads() {
		
		System.out.println(System.getenv("HUGGINGFACE_TOKEN"));

	}

}
