package com.ITCube.Booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

/**
 * @author Matteo Rosso
 */

@SpringBootApplication
@EntityScan(basePackages = "com.ITCube.Data.model")
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

	@Bean
	public Clock getClock(){
		return Clock.systemDefaultZone();
	}

}
