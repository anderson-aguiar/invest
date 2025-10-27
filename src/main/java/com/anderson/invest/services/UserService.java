package com.anderson.invest.services;

import com.anderson.invest.dtos.UserMinDTO;
import com.anderson.invest.dtos.UserRequestDTO;
import com.anderson.invest.entities.User;
import com.anderson.invest.mappers.UserMapper;
import com.anderson.invest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public UserMinDTO insert(UserRequestDTO requestDTO) {
        User user = userMapper.toEntity(requestDTO);
        userRepository.save(user);
        return userMapper.toUserMinDTO(user);
    }

}
