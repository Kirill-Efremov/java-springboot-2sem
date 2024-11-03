package itis.spring.services;

import itis.spring.dto.UserDto;
import itis.spring.models.User;
import java.security.Principal;
import java.util.List;

public interface UserService {
    boolean isEmailExists(String email);
    boolean creatUser(User user);
    List<UserDto> list();
    void banUser(User user);
    void changeUserRoles(User user, String selectedRole, User currentUser);
    User getUserByPrincipal(Principal principal);
    void updateUser(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    void updateUserPassword(User user, String newPassword);
}
