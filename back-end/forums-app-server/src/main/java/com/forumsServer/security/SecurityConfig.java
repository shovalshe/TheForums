/*
Security configuration for the forums server.
It handles the permissions, authorization.
 */

package com.forumsServer.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;


    /**
     * This is where we configure the security required for our endpoints and set up our app to serve as
     * an OAuth2 Resource Server, using JWT validation.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/forum/**").authenticated()
                .mvcMatchers(HttpMethod.POST, "/api/forum").hasAuthority("SCOPE_admin")
                .mvcMatchers(HttpMethod.POST, "/api/forum/*/post/**").authenticated()
                .mvcMatchers(HttpMethod.DELETE, "/api/forum/**").hasAuthority("SCOPE_admin")
                .mvcMatchers(HttpMethod.GET, "/api/user/**").authenticated()
                .mvcMatchers("/api/chat/**").authenticated()
                .and().cors()
                .and().oauth2ResourceServer().jwt();
    }

    /**
     * Ensure both the audience claim (aud) and the issuer claim (iss) are validated.
     */
    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}