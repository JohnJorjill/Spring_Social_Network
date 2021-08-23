package com.jorjill.reddit.service;

import com.jorjill.reddit.dto.PostRequest;
import com.jorjill.reddit.dto.PostResponse;
import com.jorjill.reddit.exceptions.PostNotFoundException;
import com.jorjill.reddit.exceptions.SpringRedditException;
import com.jorjill.reddit.exceptions.SubredditNotFoundException;
import com.jorjill.reddit.mapper.PostMapper;
import com.jorjill.reddit.model.Post;
import com.jorjill.reddit.model.Subreddit;
import com.jorjill.reddit.model.User;
import com.jorjill.reddit.repository.PostRepository;
import com.jorjill.reddit.repository.SubredditRepository;
import com.jorjill.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(()->new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();
        postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly=true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        System.out.println(subredditId);
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(()->new SpringRedditException("No subreddit found with ID - "+subredditId));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(toList());
    }
}
