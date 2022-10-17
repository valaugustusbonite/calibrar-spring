package com.calibrar.identityservice.config.security.manager;

import com.calibrar.identityservice.common.enums.UserStatus;
import com.calibrar.identityservice.common.exception.EntityNotFoundException;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.database.UserGatewayImpl;
import com.calibrar.identityservice.gateway.database.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final Optional<User> user = userRepository.findByEmail(authentication.getName());

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        if (!(bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.get().getPassword()))) {
            throw new BadCredentialsException("Invalid password");
        }

        if (user.get().getStatus() == UserStatus.DELETED) {
            throw new RuntimeException("Deleted users are not allowed to login anymore");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.get().getPassword());

    }
}
