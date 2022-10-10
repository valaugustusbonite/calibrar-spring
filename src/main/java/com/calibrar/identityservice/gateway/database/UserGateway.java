package com.calibrar.identityservice.gateway.database;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface UserGateway {
    UserDto createUser(UserDto user);
    UserDto getUserByEmail(String email);
    UserDto getUser(Long id);
    String deleteUser(Long id);
    UserDto updateUser(Long id, UserDto userInput);
}
