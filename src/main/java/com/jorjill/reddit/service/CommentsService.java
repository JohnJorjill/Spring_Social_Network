package com.jorjill.reddit.service;

import com.jorjill.reddit.dto.CommentsDto;
import com.jorjill.reddit.exceptions.PostNotFoundException;
import com.jorjill.reddit.mapper.CommentMapper;
import com.jorjill.reddit.model.Comment;
import com.jorjill.reddit.model.NotificationEmail;
import com.jorjill.reddit.model.Post;
import com.jorjill.reddit.model.User;
import com.jorjill.reddit.repository.CommentRepository;
import com.jorjill.reddit.repository.PostRepository;
import com.jorjill.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentsService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto){
        // find post by id
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(()->new PostNotFoundException(commentsDto.getPostId().toString()));

        // map commentsDto to comment
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        // save comment
        commentRepository.save(comment);

        // send email
        String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user){
        // send mail
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post!", user.getEmail(),message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        // find post
        Post post = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException(postId.toString()));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName){
        User user = userRepository.findByUsername(userName).orElseThrow(()->new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }
}
