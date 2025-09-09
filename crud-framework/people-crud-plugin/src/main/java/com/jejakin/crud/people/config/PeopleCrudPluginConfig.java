package com.jejakin.crud.people.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = "com.jejakin.crud.people")
@EnableMongoRepositories(basePackages = "com.jejakin.crud.people.repository")
public class PeopleCrudPluginConfig {
}