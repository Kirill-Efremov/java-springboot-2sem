package itis.spring.services.impl;

import itis.spring.dto.UserDto;
import itis.spring.dto.mapper.UserMapper;
import itis.spring.models.Role;
import itis.spring.models.User;
import itis.spring.repository.UserRepository;
import itis.spring.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public boolean creatUser(User user){
        String email = user.getEmail();
        if(userRepository.findByEmail(email)!= null ) return false ;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("Save new User - email: {} , name {} ",  email ,user.getName());
        return true;
    }
    @Override
    public List<UserDto> list() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public void banUser(User user ) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {} , name {}", user.getId(), user.getEmail() , user.getName());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        userRepository.save(user);
    }
    @Override
    public void changeUserRoles(User user, String selectedRole, User currentUser) {
        if (currentUser.getRoles().contains(Role.ROLE_SUPER_ADMIN)) {
            return;
        }
        user.getRoles().clear();
        user.getRoles().add(Role.valueOf(selectedRole));
        userRepository.save(user);
    }
    @Override
    public User getUserByPrincipal(Principal principal) {
        if(principal == null) return  new User();
        return userRepository.findByEmail(principal.getName());
    }
    @Override
    public void updateUser(User user) {
        log.info("Update user :  ", user);
        userRepository.save(user);
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
