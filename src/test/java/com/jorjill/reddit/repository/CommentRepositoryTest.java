package com.jorjill.reddit.repository;

import com.jorjill.reddit.model.Comment;
import com.jorjill.reddit.model.Post;
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
class CommentRepositoryTest {

    @Autowired
    private CommentRepository underTest;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByPostTest1(){
        // given
        Comment comment = new Comment();
        Post post = new Post();
        postRepository.save(post);
        comment.setCreatedDate(Instant.now());
        comment.setPost(post);
        comment.setText("Comment test");
        User user = new User();
        userRepository.save(user);
        comment.setUser(user);
        underTest.save(comment);
        // when
        List<Comment> expected = underTest.findByPost(post);
        // then
        assertThat(expected.get(0)).isEqualTo(comment);
    }

    @Test
    void findByPostTest2(){
        // given
        Post post = new Post();
        postRepository.save(post);
        List<Comment> result = new ArrayList<Comment>();
        // when
        List<Comment> expected = underTest.findByPost(post);
        // then
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void findAllByUserTest1(){
        // given
        Comment comment = new Comment();
        Post post = new Post();
        postRepository.save(post);
        comment.setCreatedDate(Instant.now());
        comment.setPost(post);
        comment.setText("Comment test");
        User user = new User();
        userRepository.save(user);
        comment.setUser(user);
        underTest.save(comment);
        // when
        List<Comment> expected = underTest.findAllByUser(user);
        // then
        assertThat(expected.get(0)).isEqualTo(comment);
    }

    @Test
    void findAllByUserTest2(){
        // given
        User user = new User();
        userRepository.save(user);
        List<Comment> result = new ArrayList<Comment>();
        // when
        List<Comment> expected = underTest.findAllByUser(user);
        // then
        assertThat(expected).isEqualTo(result);
    }

}