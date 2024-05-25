package com.example.diploma.service;

import com.example.diploma.dto.RoleDto;
import com.example.diploma.dto.UserDto;
import com.example.diploma.entity.Role;
import com.example.diploma.entity.User;
import com.example.diploma.mapper.UserMapper;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.repository.UserRepository;

import com.example.diploma.security.AuthUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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

    public UserDto updateUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDto getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();

            return userRepository.findByUsername(username)
                    .map(userMapper::toDto)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь с указанным username не найден"));
        }

        return null;
    }

    public Boolean banUser(UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setIsLocked(true);
                    userRepository.save(user);
                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("err"));
    }
}
