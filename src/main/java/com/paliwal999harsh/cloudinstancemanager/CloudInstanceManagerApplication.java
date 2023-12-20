package com.paliwal999harsh.cloudinstancemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
@EnableScheduling
public class CloudInstanceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudInstanceManagerApplication.class, args);
	}

}
