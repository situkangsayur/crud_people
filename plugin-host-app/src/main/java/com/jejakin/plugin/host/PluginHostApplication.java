package com.jejakin.plugin.host;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import com.jejakin.crud.people.config.PeopleCrudPluginConfig;

@SpringBootApplication
@Import({PeopleCrudPluginConfig.class})
@EntityScan(basePackages = {"com.jejakin.crud.people.model"})
public class PluginHostApplication {

    public static void main(String[] args) {
        SpringApplication.run(PluginHostApplication.class, args);
    }

}