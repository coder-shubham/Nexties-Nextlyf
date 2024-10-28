package com.example.EmailSender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.EmailSender"})
@EnableSolrRepositories(basePackages = "com.example.EmailSender.repository")
public class EmailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSenderApplication.class, args);
	}

}
