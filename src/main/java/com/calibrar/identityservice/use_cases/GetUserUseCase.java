package com.calibrar.identityservice.use_cases;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.gateway.database.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserUseCase {
    private final UserGateway userGateway;

    public UserDto getUser(Long id) {
        return userGateway.getUser(id);
    }
}
