package com.springboot.jpa.config.security;

import com.springboot.jpa.config.security.jwt.JWTAuthenticationFilter;
import com.springboot.jpa.config.security.jwt.JWTAuthorizationFilter;
import com.springboot.jpa.data.repository.user.UserRepository;
import com.springboot.jpa.exception.SecurityExceptionHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
	private final UserRepository userRepository;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/swagger-ui/**"
			,   "/api-docs"
			,   "/swagger-ui.html"
			,   "/api-docs/**"
			,   "/h2-console/**"
		);
	}
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			//.addFilter(getJWTAuthenticationFilter(authenticationManager())) // 인증 + 토큰 발급
			.addFilter(new JWTAuthorizationFilter(authenticationManager(), userRepository, passwordEncoder())) // 검사
			.authorizeRequests()
			.mvcMatchers(
					HttpMethod.POST
				,	"/api/v1/login"
				,	"/api/v1/user"
			)
			.permitAll()
			.mvcMatchers(HttpMethod.GET, "/api/v1/app/version/**")
			.permitAll()
			.anyRequest().authenticated()
			// .anyRequest().permitAll()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new SecurityExceptionHandler()); // 시큐리티에서 발생하는 예외 처리
	}

	public JWTAuthenticationFilter getJWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		final JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager);
		filter.setFilterProcessesUrl("/api/v1/login"); // 로그인 요청 주소 ( 기본값 : POST /login )
		return filter;
	}
}