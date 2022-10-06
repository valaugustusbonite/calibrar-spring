package com.calibrar.identityservice.use_cases;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.gateway.database.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final UserGateway userGateway;

    public UserDto updateUser(Long id, UserDto userInput) {
        return userGateway.updateUser(id, userInput);
    }
}
