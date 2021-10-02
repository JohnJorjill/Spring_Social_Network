package com.jorjill.reddit.repository;

import com.jorjill.reddit.model.Comment;
import com.jorjill.reddit.model.Post;
import com.jorjill.reddit.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
    }

    @Test
    void findByPostTestReturnsComment(){
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
        // saving comment with post
        commentRepository.save(comment);

        // when
        // find comment by post
        List<Comment> expected = commentRepository.findByPost(post);

        // then
        // returned comment is equal to comment
        assertThat(expected.get(0)).isEqualTo(comment);
    }

    @Test
    void findByPostTestReturnsEmpty(){
        // given
        Post post = new Post();
        postRepository.save(post);
        List<Comment> result = new ArrayList<Comment>();
        // when
        List<Comment> expected = commentRepository.findByPost(post);
        // then
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void findAllByUserTestReturnsValue(){
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
        commentRepository.save(comment);
        // when
        List<Comment> expected = commentRepository.findAllByUser(user);
        // then
        assertThat(expected.get(0)).isEqualTo(comment);
    }

    @Test
    void findAllByUserTestReturnsEmpty(){
        // given
        User user = new User();
        userRepository.save(user);
        List<Comment> result = new ArrayList<Comment>();
        // when
        List<Comment> expected = commentRepository.findAllByUser(user);
        // then
        assertThat(expected).isEqualTo(result);
    }

}