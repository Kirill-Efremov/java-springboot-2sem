package itis.spring.repository;

import itis.spring.models.LikePost;
import itis.spring.models.Post;
import itis.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findByUser(User user);

    LikePost findByUserAndPost(User user, Post post);

    void deleteByPost(Post post);
}
