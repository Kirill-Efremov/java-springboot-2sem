package itis.spring.dto;

import itis.spring.models.Role;
import itis.spring.models.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;


@Data
public class UserDto {
    private Long id;
    private String email;
    private String numberPhone;
    private String name;
    private boolean active;
    private Set<Role> roles;
}
