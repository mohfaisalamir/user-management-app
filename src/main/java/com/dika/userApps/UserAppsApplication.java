package com.dika.userApps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

	@SpringBootApplication
	@EnableCaching
	public class UserAppsApplication {
		public static void main(String[] args) {
			SpringApplication.run(UserAppsApplication.class, args);
		}

	}
