package com.calibrar.identityservice.config.security.manager;

import com.calibrar.identityservice.domain.dto.UserDto;
import com.calibrar.identityservice.gateway.database.UserGatewayImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private UserGatewayImpl userGateway;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDto user = userGateway.getUserByEmail(authentication.getName());


        if (!(bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());

    }
}
