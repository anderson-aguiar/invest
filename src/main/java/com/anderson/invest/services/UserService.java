package com.anderson.invest.services;

import com.anderson.invest.dtos.LoginRequestDTO;
import com.anderson.invest.dtos.UserMinDTO;
import com.anderson.invest.dtos.UserRequestDTO;
import com.anderson.invest.entities.User;
import com.anderson.invest.mappers.UserMapper;
import com.anderson.invest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }

}
