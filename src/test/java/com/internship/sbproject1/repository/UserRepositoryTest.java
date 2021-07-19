package com.internship.sbproject1.repository;

import com.internship.sbproject1.TestCreationFactory;
import com.internship.sbproject1.entity.User;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest extends TestCreationFactory  {

    @Autowired
    private UserRepository userRepository;

    private  User user = this.getUser();

    @Test
    public void findUserByEmailTest() {
        User saved = userRepository.save(user);

        Optional isSaved = userRepository.findUserByEmail(user.getEmail());

        assertThat(isSaved).isPresent();
    }
}