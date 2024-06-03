package com.example.diploma.service;

import com.example.diploma.dto.RoleDto;
import com.example.diploma.dto.UserDto;
import com.example.diploma.entity.RefreshToken;
import com.example.diploma.entity.Role;
import com.example.diploma.entity.User;
import com.example.diploma.mapper.UserMapper;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.repository.UserRepository;

import com.example.diploma.security.AuthUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    private final RefreshTokenService refreshTokenService;

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

    public Boolean banUser(UUID id) {
        List<RefreshToken> refreshTokens = refreshTokenService.findByUserId(id);
        refreshTokens.forEach(refreshTokenService::deleteRefreshToken);
        return userRepository.findById(id)
                .map(user -> {
                    user.setIsLocked(true);
                    userRepository.save(user);
                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);
    }

    public Boolean unbanUser(UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setIsLocked(false);
                    userRepository.save(user);
                    return Boolean.FALSE;
                }).orElse(Boolean.TRUE);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("err"));
    }
}
