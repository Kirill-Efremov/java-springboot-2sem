package itis.spring.controllers;

import itis.spring.dto.PostDto;
import itis.spring.dto.UserDto;
import itis.spring.dto.mapper.UserMapper;
import itis.spring.models.Post;
import itis.spring.models.User;
import itis.spring.services.FileService;
import itis.spring.services.PostService;
import itis.spring.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/post")
public class PostRestController {
    private final PostService postService;
    private final UserService userService;
    private final FileService fileService;
    private final UserMapper userMapper;

    @GetMapping()
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(name ="title", required = false) String title) {
        List<PostDto> posts = postService.getListPosts(title);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostDtoById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping()
    public ResponseEntity<String> createPost(@RequestParam(value = "file", required = false) MultipartFile file,
                                             @RequestParam("title") String title,
                                             @RequestParam("content") String content,
                                             @RequestParam("price") Long price,
                                             @RequestParam("city") String city,
                                             Principal principal) {
        if (title.isEmpty() || content.isEmpty() || price == null || city.isEmpty()) {
            return ResponseEntity.badRequest().body("Невозможно создать пост с пустыми полями.");
        }
        User user = userService.getUserByPrincipal(principal);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Не удалось определить пользователя.");
        }

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setPrice(price);
        post.setCity(city);
        postService.savePost(user, post);

        if (file != null && !file.isEmpty()) {
            try {
                fileService.saveFileToStoragePost(post.getId(), file.getInputStream(),
                        file.getOriginalFilename(),
                        file.getContentType(),
                        file.getSize());
                return ResponseEntity.ok("Пост успешно создан с фотографией!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при сохранении файла!");
            }
        }

        return ResponseEntity.ok("Пост успешно создан!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id){
        postService.delete(id);
        return ResponseEntity.ok("Пост успешно удален.");
    }

    @PostMapping("/{id}/toggle-favorite")
    public ResponseEntity<String> toggleFavorite(@PathVariable Long id, Principal principal) {
        Post post = postService.getPostById(id);
        User user = userService.getUserByPrincipal(principal);
        if (user != null && post != null) {
            boolean isLiked = postService.toggleFavorite(user, post);
            if (isLiked) {
                return ResponseEntity.ok("Пост добавлен в избранное!");
            } else {
                return ResponseEntity.ok("Пост удален из избранного!");
            }
        } else {
            return ResponseEntity.badRequest().body("Не удалось обработать запрос.");
        }
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<PostDto>> getFavoritePosts(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        if (user != null) {
            List<PostDto> favoritePosts = postService.getLikedPostsByUser(user);
            return ResponseEntity.ok(favoritePosts);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody Post post, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingPost.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Недостаточно прав для обновления этого поста.");
        }
        if (post.getTitle().isEmpty() || post.getContent().isEmpty() || post.getPrice() == null || post.getCity().isEmpty()) {
            return ResponseEntity.badRequest().body("Невозможно обновить пост с пустыми полями.");
        } else {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setPrice(post.getPrice());
            existingPost.setCity(post.getCity());
            postService.updatePost(existingPost);
            return ResponseEntity.ok("Пост успешно обновлен!");
        }
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<UserDto> getUserByPostId(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            User user = post.getUser();
            if (user != null) {
                UserDto userDto = userMapper.toDto(user);
                return ResponseEntity.ok(userDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

