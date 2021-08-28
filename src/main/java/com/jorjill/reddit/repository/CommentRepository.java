package com.jorjill.reddit.repository;

import com.jorjill.reddit.model.Comment;
import com.jorjill.reddit.model.Post;
import com.jorjill.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
