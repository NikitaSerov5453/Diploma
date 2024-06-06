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
import com.example.diploma.validation.UserValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

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

//    private final RefreshTokenService refreshTokenService;

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


    public UserDto updateUser(Optional<User> userEntity, UserDto userDto) {
        userEntity.get().setUsername(userDto.getUsername());
        userEntity.get().getRole().setRoleType(userDto.getRole().getRoleType());
        if (!userEntity.get().getPassword().equals(userDto.getPassword())) {
            userEntity.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userEntity.get().getEmployee().setName(userDto.getEmployee().getName());
        userEntity.get().getEmployee().setLastName(userDto.getEmployee().getLastName());
        userEntity.get().getEmployee().setPatronymicName(userDto.getEmployee().getPatronymicName());

        return userMapper.toDto(userRepository.save(userEntity.get()));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findUserById(id);
    }

    public Boolean banUnbanUser(UUID id) {

        Optional<User> optional = userRepository.findById(id);
        if (optional.get().getIsLocked()) {
            optional.map(user -> {
                        user.setIsLocked(false);
                        userRepository.save(user);
                        return Boolean.FALSE;
                    });
            return false;
        } else {
            optional.map(user -> {
                        user.setIsLocked(true);
                        userRepository.save(user);
                        return Boolean.TRUE;
                    });
            return true;
        }
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("err"));
    }
}
