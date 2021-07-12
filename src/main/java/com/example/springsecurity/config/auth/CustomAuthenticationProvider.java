package com.example.springsecurity.config.auth;

import com.example.springsecurity.domain.user.UserDetailsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token =
                (UsernamePasswordAuthenticationToken) authentication;

        String userId = token.getName();
        String userPw = (String) token.getCredentials();

        UserDetailsVo userDetailsVo = (UserDetailsVo) userDetailsService.loadUserByUsername(userId);

        if(!passwordEncoder.matches(userPw, userDetailsVo.getPassword())) {
            throw new BadCredentialsException(userDetailsVo.getUsername() + "Invalid password");
        }

        // 위의 로직을 통해 인증에 성공한 경우 인증된 UsernamePasswordAuthenticationToken 리턴
        return new UsernamePasswordAuthenticationToken(userDetailsVo, userPw, userDetailsVo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
