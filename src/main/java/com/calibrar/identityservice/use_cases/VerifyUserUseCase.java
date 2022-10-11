package com.calibrar.identityservice.use_cases;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.gateway.database.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyUserUseCase {
    private final UserGateway userGateway;

    public UserDto verifyUser(Authentication auth) {
        return userGateway.verifyUser(auth);
    }
}
