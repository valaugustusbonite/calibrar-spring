package com.calibrar.identityservice.gateway.database;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import org.springframework.stereotype.Component;

public interface UserGateway {
    UserDto createUser(UserDto user);
}
