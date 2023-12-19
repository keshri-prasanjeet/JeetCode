package org.jeet.JeetCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class JeetCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JeetCodeApplication.class, args);
	}

}
