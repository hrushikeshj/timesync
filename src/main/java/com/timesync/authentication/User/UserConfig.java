package com.timesync.authentication.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            User a = new User("f", "d@g.com", "f");
            User b = new User("h", "d@d.com", "f");

//            repository.saveAll(
//                    List.of(a, b)
//            );
        };
    }
}
