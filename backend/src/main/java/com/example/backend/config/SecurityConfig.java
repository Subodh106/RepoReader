package com.example.backend.config;

import com.example.backend.security.GithubOAuth2UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.*;

@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationSuccessHandler oauth2successHandler;
   private final AuthenticationFailureHandler oauth2failureHandler;
   private final GithubOAuth2UserService githubOAuth2UserService;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(csfr->csfr.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/login-url",
                                "/oauth2/**",
                                "login/oauth2/**",
                                "error").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .anyRequest().permitAll())
                .exceptionHandling(ex->ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(oauth->oauth
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(githubOAuth2UserService))
                        .successHandler(oauth2successHandler)
                        .failureHandler(oauth2failureHandler))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(
                                ((request, response, authentication) -> response.setStatus(HttpStatus.NO_CONTENT.value()))
                        )
                        .invalidateHttpSession(true)
                        .deleteCookies("REPOREADER_SESSION"));
        return httpSecurity.build();
    }

    @Bean
    AuthenticationSuccessHandler oauth2successHandler(@Value("${app.frontend-url}")String frontendUrl) {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl(frontendUrl+"/auth/cllback");
        return handler;
    }
    @Bean
    AuthenticationFailureHandler oauth2failureHandler(@Value("${app.frontend-url}")String frontendUrl) {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl(frontendUrl+"/login?error=oauth2");
        return handler;
    }
    }
