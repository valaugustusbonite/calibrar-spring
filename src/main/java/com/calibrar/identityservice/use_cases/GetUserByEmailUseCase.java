package com.calibrar.identityservice.use_cases;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.database.UserGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserByEmailUseCase {

    private final UserGateway userGateway;

    public UserDto getUserByEmail(String email) {
        return userGateway.getUserByEmail(email);
    }
}
