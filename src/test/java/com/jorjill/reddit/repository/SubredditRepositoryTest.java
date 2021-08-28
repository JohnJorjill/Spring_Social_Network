package com.jorjill.reddit.repository;

import com.jorjill.reddit.exceptions.SpringRedditException;
import com.jorjill.reddit.model.Post;
import com.jorjill.reddit.model.Subreddit;
import com.jorjill.reddit.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class SubredditRepositoryTest {

    @Autowired
    private SubredditRepository underTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void checkIfSubredditExists(){
        // given
        Subreddit subreddit = new Subreddit();
        User user = new User();
        userRepository.save(user);
        List<Post> posts = new ArrayList<Post>();
        Post post = new Post();
        postRepository.save(post);
        posts.add(post);
        String name = "Test";
        subreddit.setDescription("Testing subreddit repository");
        subreddit.setCreatedDate(Instant.now());
        subreddit.setName("Test");
        subreddit.setUser(user);
        subreddit.setPosts(posts);
        underTest.save(subreddit);
        //when
        Subreddit expected = underTest.findByName(name).orElseThrow(()->new SpringRedditException("User not found"));
        //then
        assertThat(expected).isEqualTo(subreddit);
    }

    @Test
    void isShouldCheckIfUserDoesntExist(){
        // given
        String name = "Test";
        // when
        Optional expected = underTest.findByName(name);
        // then
        assertThat(expected).isEmpty();
    }

}