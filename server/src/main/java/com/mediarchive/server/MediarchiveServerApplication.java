package com.mediarchive.server;

import com.mediarchive.server.domain.User;
import com.mediarchive.server.service.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MediarchiveServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediarchiveServerApplication.class, args);
    }
}
