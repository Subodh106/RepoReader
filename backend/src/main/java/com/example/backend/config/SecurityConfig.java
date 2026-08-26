package com.example.backend.config;

import com.example.backend.security.GithubOAuth2UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final GithubOAuth2UserService githubOAuth2UserService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers
                                ("/api/auth/login-url",
                                        "/api/oauth2/**",
                                        "/login/oauth2/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                ).exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(githubOAuth2UserService))
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)

                )
                .logout(logout->logout.logoutUrl("/api/auth/logout").logoutSuccessHandler((request, response, authentication) ->response.setStatus(HttpStatus.NO_CONTENT.value())
                        )
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("READREPO_SESSION")
);
        return httpSecurity.build();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler(@Value("${app.frontend-url}")String frontendUrl){
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl(frontendUrl+"/auth/callback");
        return handler;
    }
    @Bean
    AuthenticationFailureHandler authenticationFailureHandler(@Value("${app.frontend-url}")String frontendUrl){
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl(frontendUrl+"/login?error=oauth_failed");
        return handler;
    }
}