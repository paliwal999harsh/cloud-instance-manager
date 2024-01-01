package com.paliwal999harsh.cloudinstancemanager.microservices.trigger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories
@ComponentScan("com.paliwal999harsh.cloudinstancemanager")
public class TriggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriggerApplication.class, args);
	}

}
