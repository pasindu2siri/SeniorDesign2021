package org.senior_design.springboot.aws.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main implementation class which serves two purpose in a spring boot application: Configuration and bootstrapping.
 * @author yatin-batra
 */
@SpringBootApplication
public class SpringS3 {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringS3.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringS3.class, args);
		LOGGER.info("SpringbootS3tutorial application started successfully.");
	}
}
