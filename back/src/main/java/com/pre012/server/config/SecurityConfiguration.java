package com.pre012.server.config;

import com.pre012.server.auth.filter.JwtAuthenticationFilter;
import com.pre012.server.auth.handler.memberAuthentication.FailureHandler;
import com.pre012.server.auth.handler.memberAuthentication.SuccessHandler;
import com.pre012.server.auth.util.JWTTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // (debug = true)
public class SecurityConfiguration {

    private final JWTTokenizer jwtTokenizer;

    public SecurityConfiguration(JWTTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // stateless(jwt)
            .cors(Customizer.withDefaults())
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .apply(new CustomFilterConfigurer())
            .and()
            .authorizeHttpRequests(authorize -> authorize
                                    .anyRequest().permitAll() // 모든 요청 허용 -> 변경 필요
            )
            ;
        return http.build(); 
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // JwtAuthenticationFilter 등록
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            // SecurityConfigurer 간 공유객체 중 AuthenticationManager 할당
            AuthenticationManager authenticationManager
                    = builder.getSharedObject(AuthenticationManager.class);

            // -- authenticationFilter
            JwtAuthenticationFilter jwtAuthenticationFilter
                    = new JwtAuthenticationFilter(authenticationManager);

            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            // SuccessHandler, FailureHandler 각각 구현 클래스 생성 -> DI가 아닌 new도 무방하다
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new SuccessHandler(jwtTokenizer));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new FailureHandler());

            builder
                    .addFilter(jwtAuthenticationFilter);
        }
    }
}