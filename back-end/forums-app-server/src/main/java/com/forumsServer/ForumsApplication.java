/*
Entrance point for the application.
 */

package com.forumsServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForumsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ForumsApplication.class, args);
	}

}
