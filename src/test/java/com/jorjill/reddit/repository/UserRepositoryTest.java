package com.jorjill.reddit.repository;

import com.jorjill.reddit.exceptions.SpringRedditException;
import com.jorjill.reddit.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfUserExists(){
        //given
        User user = new User();
        String username = "Johnny";
        user.setUsername(username);
        user.setEmail("johnny@email.com");
        user.setPassword("123");
        user.setCreated(Instant.now());
        user.setEnabled(false);
        underTest.save(user);
        //when
        User expected = underTest.findByUsername(username).orElseThrow(()->new SpringRedditException("User not found"));
        //then
        assertThat(expected).isEqualTo(user);
    }

    @Test
    void isShouldCheckIfUserDoesntExist(){
        // given
        String email = "jack@email.com";
        // when
        Optional expected = underTest.findByUsername("jack");
        // then
        assertThat(expected).isEmpty();
    }

}
