package com.example.springsecurity.config.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) {
        // 정적 자원에 대해서는 Security 설정을 적용하지 않음.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                        .antMatchers("/index").hasRole("USER")
                        .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**").hasRole("USER")
                        .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/user/loginForm")
                        .successForwardUrl("/index")
                        // .loginProcessingUrl("/user/login")
                        .failureForwardUrl("/index").permitAll()
                .and()
                    .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .csrf().disable()
                .csrf()
                    .requireCsrfProtectionMatcher(new CsrfRequireMatcher())
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }

    static class CsrfRequireMatcher implements RequestMatcher {
        private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

        @Override
        public boolean matches(HttpServletRequest request) {
            if (ALLOWED_METHODS.matcher(request.getMethod()).matches())
                return false;

            final String referer = request.getHeader("Referer");
            if (referer != null && referer.contains("/swagger-ui")) {
                return false;
            }
            return true;
        }
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        // CustomAuthenticationFilter 생성 (인자로 AuthenticationManager를 넘겨줌)
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());

        // 실제 인증을 처리할 URL 지정
        customAuthenticationFilter.setFilterProcessesUrl("/user/login");

        // 인증에 성공했을 때의 CustomLoginSuccessHandler 지정
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());

        // afterPropertiesSet()메소드를 호출하여 AuthenticationManager가 정상적으로 설정되었는지 검증
        customAuthenticationFilter.afterPropertiesSet();

        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder());
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }

}
