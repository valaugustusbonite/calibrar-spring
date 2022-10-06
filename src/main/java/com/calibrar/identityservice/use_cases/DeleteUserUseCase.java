package com.calibrar.identityservice.use_cases;

import com.calibrar.identityservice.gateway.database.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserGateway userGateway;

    public String deleteUser(Long id) {
        return userGateway.deleteUser(id);
    }
}
