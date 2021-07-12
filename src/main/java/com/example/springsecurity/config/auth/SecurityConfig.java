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
        http.
                csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                        .antMatchers("/index").hasAnyRole("USER")
                        .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/user/loginForm")
                        .successForwardUrl("/index")
                        // .loginProcessingUrl("/user/login")
                        .failureForwardUrl("/index").permitAll()
                .and()
                    .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

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
