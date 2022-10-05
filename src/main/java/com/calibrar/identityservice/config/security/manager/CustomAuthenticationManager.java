package com.calibrar.identityservice.config.security.manager;

import com.calibrar.identityservice.common.exception.EntityNotFoundException;
import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.domain.entity.User;
import com.calibrar.identityservice.gateway.database.UserGateway;
import com.calibrar.identityservice.gateway.database.UserGatewayImpl;
import com.calibrar.identityservice.gateway.database.UserRepository;
import com.calibrar.identityservice.use_cases.GetUserByEmailUseCase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserGatewayImpl userGateway;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDto user = userGateway.getUserByEmail(authentication.getName());


        if (!(bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))) {
            System.out.println("NOT AUTHORIZED");
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getCredentials(), user.getPassword());

    }
}
