package com.anderson.invest.mappers;

import com.anderson.invest.dtos.UserRequestDTO;
import com.anderson.invest.dtos.UserMinDTO;
import com.anderson.invest.entities.Role;
import com.anderson.invest.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserMapper {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User toEntity(UserRequestDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return user;
    }

    public UserMinDTO toUserMinDTO(User entity){
        if (entity == null) return null;

        UserMinDTO dto = new UserMinDTO();
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        String role = entity.getRole() == Role.ROLE_USER ? "user" : "admin";
        dto.setRole(role);
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }
}
