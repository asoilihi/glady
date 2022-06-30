package com.backend.as.glady;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author asoilihi
 *
 */
@SpringBootApplication
@EnableRetry
public class GladyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GladyApplication.class, args);
	}

}
