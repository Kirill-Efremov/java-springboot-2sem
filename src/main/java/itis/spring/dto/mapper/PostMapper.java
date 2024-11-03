package itis.spring.dto.mapper;

import itis.spring.dto.PostDto;
import itis.spring.models.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPrice(post.getPrice());
        dto.setCity(post.getCity());
        dto.setUserId(post.getUser().getId());
        dto.setPhoto(post.getPhoto());
        return dto;
    }

    public Post toEntity(PostDto dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setPrice(dto.getPrice());
        post.setCity(dto.getCity());
        post.setPhoto(dto.getPhoto());
        return post;
    }
}
