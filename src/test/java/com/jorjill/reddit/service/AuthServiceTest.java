package com.jorjill.reddit.service;

import com.jorjill.reddit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private AuthService underTest;

    @BeforeEach
    void setUp(){
        //underTest = new AuthService();
    }

    @Test
    void signupTest(){

    }

}