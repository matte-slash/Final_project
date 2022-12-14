package com.ITCube.Desk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author Matteo Rosso
 */

@SpringBootApplication
@EntityScan(basePackages = "com.ITCube.Data.model")
public class DeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeskApplication.class, args);
	}

}
