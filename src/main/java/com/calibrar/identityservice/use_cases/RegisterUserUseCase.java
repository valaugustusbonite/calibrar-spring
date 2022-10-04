package com.calibrar.identityservice.use_cases;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.database.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserGateway userGateway;

    public UserDto createUser(UserDto user) {
        return userGateway.createUser(user);
    }
}
