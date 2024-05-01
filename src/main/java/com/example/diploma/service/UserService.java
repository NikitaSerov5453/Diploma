package com.example.diploma.service;

import com.example.diploma.dto.RoleDto;
import com.example.diploma.dto.UserDto;
import com.example.diploma.entity.Role;
import com.example.diploma.entity.User;
import com.example.diploma.mapper.UserMapper;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    public UserDto addUser(UserDto userDto) {
        RoleDto role = userDto.getRole();

        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePassword);

        User entity = userMapper.toEntity(userDto);
        Optional<Role> optional;
        if (role.getId() != null) {
            optional = roleRepository.findById(role.getId());
        } else {
            optional = roleRepository.findByRoleType(role.getRoleType());
        }
        optional.ifPresent(entity::setRole);

        return userMapper.toDto(userRepository.save(entity));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
