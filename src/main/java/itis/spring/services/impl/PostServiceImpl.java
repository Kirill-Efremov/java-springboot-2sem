package itis.spring.services.impl;

import itis.spring.dto.PostDto;
import itis.spring.dto.mapper.PostMapper;
import itis.spring.models.LikePost;
import itis.spring.models.Post;
import itis.spring.models.User;
import itis.spring.repository.LikePostRepository;
import itis.spring.repository.PostRepository;
import itis.spring.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final PostMapper postMapper;
    @Override
    public void savePost(User user, Post post) {
        post.setUser(user);
        postRepository.save(post);
        log.info("LOG - Save post {} ", post);
    }
    @Override
    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост не найден"));
        likePostRepository.deleteByPost(post);
        postRepository.delete(post);
        log.info("LOG - Delete post {}", post);
    }
    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пост с id " + id + " не найден."));
    }
    public PostDto getPostDtoById(Long id) {
        return postMapper.toDto(postRepository.getById(id));
    }

    @Override
    public List<PostDto> getLikedPostsByUser(User user) {
        List<LikePost> likedPosts = likePostRepository.findByUser(user);
        return likedPosts.stream()
                .map(likePost -> postMapper.toDto(likePost.getPost()))
                .collect(Collectors.toList());
    }
    @Override
    public List<PostDto> getPostsByUserId(User user) {
        return postRepository.findAllByUser(user).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<PostDto> getListPosts(String title) {
        if(title != null && !title.isEmpty())  return  postRepository.findByTitleWithTrigram(title).stream().map(postMapper::toDto).collect(Collectors.toList());
        return postRepository.findAll().stream().map(postMapper::toDto).collect(Collectors.toList());
    }
    @Override
    public boolean toggleFavorite(User user, Post post) {
        if (user == null || post == null) {
            throw new IllegalArgumentException("Невозможно добавить/удалить в избранное. Пользователь или пост отсутствуют.");
        }
        LikePost likePost = likePostRepository.findByUserAndPost(user, post);
        if (likePost != null ) {
            likePostRepository.delete(likePost);
            log.info("LOG - Post delete from liked posts {}", likePost);
            return false;
        } else {
            likePost = new LikePost();
            likePost.setUser(user);
            likePost.setPost(post);
            likePostRepository.save(likePost);
            log.info("LOG - Post save to liked posts {}", likePost);
            return true;
        }
    }
    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
        log.info("LOG - Update post {}", post);
    }
}
