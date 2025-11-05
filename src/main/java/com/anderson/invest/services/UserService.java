package com.anderson.invest.services;

import com.anderson.invest.dtos.UserMinDTO;
import com.anderson.invest.dtos.UserRequestDTO;
import com.anderson.invest.entities.User;
import com.anderson.invest.mappers.UserMapper;
import com.anderson.invest.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Transactional
    public UserMinDTO insert(UserRequestDTO requestDTO) {

        if(userRepository.existsByEmail(requestDTO.getEmail())){
            throw new RuntimeException("Email já cadastrado");
        }
        User user = userMapper.toEntity(requestDTO);
        userRepository.save(user);
        return userMapper.toUserMinDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }

}
