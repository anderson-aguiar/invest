package com.anderson.invest.services;

import com.anderson.invest.dtos.UserMinDTO;
import com.anderson.invest.dtos.UserRequestDTO;
import com.anderson.invest.entities.User;
import com.anderson.invest.exceptions.EmailAlreadyExistsException;
import com.anderson.invest.exceptions.RabbitMQException;
import com.anderson.invest.mappers.UserMapper;
import com.anderson.invest.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailPublisherService emailPublisherService;

    public UserService(UserRepository userRepository, UserMapper userMapper, EmailPublisherService emailPublisherService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.emailPublisherService = emailPublisherService;
    }


    @Transactional
    public UserMinDTO insert(UserRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email já cadastrado");
        }

        User user = userMapper.toEntity(requestDTO);
        userRepository.save(user);
        UserMinDTO response = userMapper.toUserMinDTO(user);

        try {
            emailPublisherService.sendWelcomeMessage(response);
        } catch (RabbitMQException e) {
            log.warn("Falha ao enviar mensagem de boas-vindas: {}", e.getMessage());
        }

        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }

}
