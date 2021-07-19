package com.internship.sbproject1.config;

import com.internship.sbproject1.entity.User;
import com.internship.sbproject1.entity.UserRole;
import com.internship.sbproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RequiredArgsConstructor
@Configuration
public class UserConfig {

        @Autowired
        public final UserRepository userRepository;

        private Random random = new Random();

        public void generateUsers(boolean b) {
            List<User> users = new ArrayList<>();
            if (b) {
                for (int i = 0; i < 50; i++) {
                    User u = this.randomUser();
                    System.out.println(u.getEmail());
                    System.out.println(u.getGender());
                    users.add(u);
                }
                userRepository.saveAll(users);
            }
        }

        public User randomUser() {
            return new User((long) random.nextInt(), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(5) + "@" + RandomStringUtils.randomAlphabetic(2) + "." + RandomStringUtils.randomAlphabetic(3), UserRole.USER, random.nextInt(2) + 1, RandomStringUtils.randomAlphabetic(10));
        }

    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository userRepository
    ) {
            try {
                boolean generate = false;
                this.generateUsers(generate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return args -> {};
    }

    }

