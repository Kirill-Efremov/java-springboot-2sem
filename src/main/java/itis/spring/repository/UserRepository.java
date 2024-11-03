package itis.spring.repository;

import itis.spring.models.FileInfo;
import itis.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.id = :userId")
    void updateAvatarForUser(@Param("userId") Long userId, @Param("avatar") FileInfo avatar);

    @Query("SELECT u.avatar FROM User u WHERE u.id = :userId")
    FileInfo findAvatarFileInfoByUserId(Long userId);
}
