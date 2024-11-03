package itis.spring.controllers;

import itis.spring.models.Post;
import itis.spring.models.User;
import itis.spring.services.PostService;
import itis.spring.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/posts")
    public String posts() {
        return "all-posts";
    }

    @GetMapping("/post/create")
    public String getPostCreate() {
        return "post-create";
    }

    @GetMapping("/posts/favourite")
    public String favouritePosts() {
        return "post-favourite";
    }

    @GetMapping("/post-info/{id}")
    public String postById() {
        return "post-info";
    }

    @GetMapping("/post-edit/{id}")
    public String editPost(@PathVariable Long id, Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        Post existingPost = postService.getPostById(id);
        if (!existingPost.getUser().getId().equals(user.getId())) {
            model.addAttribute("errorMessage", "Недостаточно прав для обновления этого поста.");
            return "access-denied";
        }
        return "post-edit";
    }
}
