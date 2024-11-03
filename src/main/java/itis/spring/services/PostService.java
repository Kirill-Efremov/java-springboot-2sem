package itis.spring.services;

import itis.spring.dto.PostDto;
import itis.spring.models.Post;
import itis.spring.models.User;

import java.util.List;

public interface PostService {void savePost(User user, Post post);

    void delete(Long postId);

    Post getPostById(Long id);

    PostDto getPostDtoById(Long id);

    List<PostDto> getLikedPostsByUser(User user);

    List<PostDto> getPostsByUserId(User user);

    List<PostDto> getListPosts(String title);

    boolean toggleFavorite(User user, Post post);

    void updatePost(Post post);
}
