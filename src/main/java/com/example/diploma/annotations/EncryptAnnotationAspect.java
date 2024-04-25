package com.example.diploma.annotations;

import com.example.diploma.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
public class EncryptAnnotationAspect {

    private final PasswordEncoder passwordEncoder;

    @Pointcut("@annotation(Encrypt) && args(userDto,..)")
    public void callEncrypt(UserDto userDto) {

    }

    @Before(value = "callEncrypt(userDto)", argNames = "userDto")
    public void beforeEncrypt(UserDto userDto) {

        String encodePassword = passwordEncoder.encode(userDto.getPassword());

        userDto.setPassword(encodePassword);
    }
}
