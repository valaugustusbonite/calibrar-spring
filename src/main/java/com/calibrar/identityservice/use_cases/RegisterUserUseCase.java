package com.calibrar.identityservice.use_cases;

import com.calibrar.identityservice.common.exception.ApiRequestException;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.database.UserGateway;
import com.calibrar.identityservice.gateway.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserGateway userGateway;

    public UserDto createUser(UserDto user) {
        return userGateway.createUser(user);
    }
}
