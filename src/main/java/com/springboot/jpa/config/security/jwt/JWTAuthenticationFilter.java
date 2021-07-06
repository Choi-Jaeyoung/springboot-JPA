package com.springboot.jpa.config.security.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.jpa.config.security.auth.PrincipalDetails;
import com.springboot.jpa.data.entity.user.UserEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;


// [인증 + 토큰발급]
// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
// '/login' 요청해서 username, password 전송하면 (post) -> 임의로 주소 변경할 수 있음..
// UsernamePasswordAuthenticationFilter 동작을 함.
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request
		,	HttpServletResponse response
	)throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter : 로그인 시도중");
                
		try {

			ObjectMapper om = new ObjectMapper();
			UserEntity user = om.readValue(request.getInputStream(), UserEntity.class);
			System.out.println("user: " + user);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
			System.out.println("authenticationToken: " + authenticationToken);
            
			// PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨.
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			System.out.println("authentication: " + authentication);
            
			return authentication;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.attemptAuthentication(request, response);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest request
		,	HttpServletResponse response
		,	FilterChain chain
		,	Authentication authResult
	) throws IOException, ServletException {
		System.out.println("successfulAuthentication 실행됨: 인증이 완료되었다는 뜻임.");

		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		System.out.println("principalDetails: " + principalDetails);

		String jwtToken = JWT.create()
							.withSubject("인증토큰")
							.withExpiresAt(new Date(System.currentTimeMillis() + JWTProperties.EXPIRATION_TIME))
							.withClaim("id", principalDetails.getUsername())
							.withClaim("password", principalDetails.getPassword())
							.sign(Algorithm.HMAC512(JWTProperties.SECRET));
		
		response.addHeader("Authorization", JWTProperties.TOKEN_PRIFIX + jwtToken);
		chain.doFilter(request, response);
        
	}


}
