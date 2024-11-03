package itis.spring.repository;

import itis.spring.models.FileInfo;
import itis.spring.models.Post;
import itis.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);

    @Query(value = "SELECT p FROM Post p WHERE similarity(p.title, :keyword) > 0.3   ORDER BY similarity(p.title, :keyword) DESC")
    List<Post> findByTitleWithTrigram(String keyword);

    @Transactional
    @Modifying
    @Query("update Post p set p.photo = :photo where p.id = :postId")
    void setPhotoForPost(@Param("postId") Long postId, @Param("photo") FileInfo photo);
}
