package com.pre012.server.config;

import com.pre012.server.auth.filter.JwtAuthenticationFilter;
import com.pre012.server.auth.filter.JwtVerificationFilter;
import com.pre012.server.auth.handler.MemberAuthenticationFailureHandler;
import com.pre012.server.auth.handler.MemberAuthenticationSuccessHandler;
import com.pre012.server.auth.handler.MemberAccessDeniedHandler;
import com.pre012.server.auth.handler.MemberAuthenticationEntryPoint;
import com.pre012.server.auth.util.CustomAuthorityUtils;
import com.pre012.server.auth.util.JWTTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;


@Configuration
@EnableWebSecurity // (debug = true)
public class SecurityConfiguration {

    private final JWTTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberAuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfiguration(JWTTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils,
                                 MemberAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().sameOrigin()
            .and()
            .csrf().disable() // stateless(jwt)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling()
            .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
            .accessDeniedHandler(new MemberAccessDeniedHandler())
            .and()
            .apply(new CustomFilterConfigurer())
            .and()
            .cors()
            .and()
            .authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                      .anyRequest().permitAll() // 우선 모든 요청 허용, 제한은 통합 후에 설정 !
//                    .mvcMatchers(HttpMethod.POST,
//                            "/members",
//                            "/auth/refresh"
//                    )
//                    .permitAll()
//                    .mvcMatchers(HttpMethod.GET,
//                            "/members/profile/**",
//                            "/members/questions/**",
//                            "/members/answers/**",
//                            "/questions/**",
//                            "/answers/**"
//                    ).permitAll()
//                    .anyRequest().authenticated()
//            )
            ;
        return http.build(); 
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Custom Filters 등록
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
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(
                    authenticationSuccessHandler);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(
                    new MemberAuthenticationFailureHandler());

            // -- verificationFilter
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder.addFilter(new CORSConfiguration().corsFilter())
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);

        }
    }

}