package com.mati.gamechat.security.config;

import com.mati.gamechat.security.config.service.PasswordEncoderServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoderServiceImpl passwordEncoder;
    private final UserDetailsService userDetailsService;

    public CustomAuthenticationProvider(PasswordEncoderServiceImpl passwordEncoder,
                                        UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("CREDENTIALS: " + authentication.getCredentials().toString());
        System.out.println("DETAILS: " + authentication.getDetails());

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetailsDb;

        try {
            userDetailsDb = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException exception) {
            throw new BadCredentialsException(exception.getMessage());
        }

        UsernamePasswordAuthenticationToken token;

        if (passwordEncoder.passwordEncoder().matches(password, userDetailsDb.getPassword())) {
            token =
                    new UsernamePasswordAuthenticationToken(
                            userDetailsDb,
                            userDetailsDb.getPassword(),
                            userDetailsDb.getAuthorities()
                    );
            token.setDetails(authentication.getDetails());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }

        SecurityContextHolder.getContext().setAuthentication(token);

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}