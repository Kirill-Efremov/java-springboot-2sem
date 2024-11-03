package itis.spring.dto.mapper;

import itis.spring.dto.UserDto;
import itis.spring.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNumberPhone(user.getNumberPhone());
        dto.setName(user.getName());
        dto.setActive(user.isActive());
        dto.setRoles(user.getRoles());
        return dto;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setNumberPhone(dto.getNumberPhone());
        user.setName(dto.getName());
        user.setActive(dto.isActive());
        user.setRoles(dto.getRoles());
        return user;
    }

}
