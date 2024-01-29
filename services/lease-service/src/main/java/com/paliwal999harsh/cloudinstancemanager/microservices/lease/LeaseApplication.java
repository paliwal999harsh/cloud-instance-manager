package com.paliwal999harsh.cloudinstancemanager.microservices.lease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
@ComponentScan("com.paliwal999harsh.cloudinstancemanager")
public class LeaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaseApplication.class, args);
	}

}
