package com.example.dockercli.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf()
                .disable()

                .headers().frameOptions().sameOrigin()

                // auth
                .and()
                .authorizeHttpRequests()
                .antMatchers(
                        "/login"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                // login
                .and()
                .formLogin()
                .successHandler(getSuccessHandler())
                .failureHandler(getAuthenticationFailureHandler())
                .usernameParameter("username")
                .passwordParameter("password")
                // logout
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                // sessionManage
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login");
        return http.build();
    }

    private static AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            System.out.println("exception : " + exception.getMessage());
            response.sendRedirect("/"); // 인증이 성공한 후에는 root로 이동
        };
    }

    private static AuthenticationSuccessHandler getSuccessHandler() {
        return (request, response, authentication) -> {
            System.out.println("authentication : " + authentication.getName());
            response.sendRedirect("/"); // 인증이 성공한 후에는 root로 이동
        };
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
}
