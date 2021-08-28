package com.jorjill.reddit.repository;

import com.jorjill.reddit.model.Post;
import com.jorjill.reddit.model.Subreddit;
import com.jorjill.reddit.model.User;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository underTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubredditRepository subredditRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findAllBySubredditTest1(){
        // given
        User user = new User();
        userRepository.save(user);
        Subreddit subreddit = new Subreddit();
        subredditRepository.save(subreddit);
        Post post = new Post();
        post.setPostName("Test");
        post.setDescription("Testing post");
        post.setCreatedDate(Instant.now());
        post.setSubreddit(subreddit);
        post.setUser(user);
        post.setVoteCount(1);
        post.setUrl("google.com");
        underTest.save(post);
        // when
        List<Post> expected = underTest.findAllBySubreddit(subreddit);
        // then
        assertThat(expected.get(0)).isEqualTo(post);
    }

    @Test
    void findAllBySubredditTest2(){
        // given
        List<Post> result = new ArrayList<Post>();
        Subreddit subreddit = new Subreddit();
        subredditRepository.save(subreddit);
        // when
        List<Post> expected = underTest.findAllBySubreddit(subreddit);
        // then
        assertThat(expected).isEqualTo(result);
    }


}