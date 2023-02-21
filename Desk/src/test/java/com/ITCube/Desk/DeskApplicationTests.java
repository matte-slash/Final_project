package com.ITCube.Desk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EntityScan(basePackages = "com.ITCube.Data")
class DeskApplicationTests {

	@Test
	void contextLoads() {
	}

}
