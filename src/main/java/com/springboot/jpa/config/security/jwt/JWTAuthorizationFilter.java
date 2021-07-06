package com.springboot.jpa.config.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springboot.jpa.config.security.auth.PrincipalDetails;
import com.springboot.jpa.data.entity.user.UserEntity;
import com.springboot.jpa.data.repository.user.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// [토큰 검사]
// 시큐리티가 filter가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음.
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안타요.
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public JWTAuthorizationFilter(
			AuthenticationManager authenticationManager
		,	UserRepository userRepository
		,	BCryptPasswordEncoder bCryptPasswordEncoder
	) {
		super(authenticationManager);
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

	@Override
	protected void doFilterInternal(
			HttpServletRequest request
		,	HttpServletResponse response
		,	FilterChain chain
	)throws IOException, ServletException {

		String jwtHeader = request.getHeader(JWTProperties.HEADER_STRING);

		// header가 있는지 확인
		if (jwtHeader == null || !jwtHeader.startsWith(JWTProperties.TOKEN_PRIFIX)) {
			chain.doFilter(request, response);
			return;
		}

		String contextPath = request.getContextPath();
		String requestUri = request.getRequestURI();
		String method = request.getMethod();

		System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
		System.out.println("ContextPath : [" + contextPath + "]");
		System.out.println("requestUri : [" + requestUri + "]");
		System.out.println("method : [" + method + "]");
		System.out.println("jwtHeader: [" + jwtHeader + "]");

		// JWT 토큰을 검증해서 정상적인 사용자인지 확인
		String jwtToken = request.getHeader(JWTProperties.HEADER_STRING).replace(JWTProperties.TOKEN_PRIFIX, "");
		String id = JWT.require(Algorithm.HMAC512(JWTProperties.SECRET)).build()
																		.verify(jwtToken)
																		.getClaim("id")
																		.asString();
		String password = JWT.require(Algorithm.HMAC512(JWTProperties.SECRET)).build()
																				.verify(jwtToken)
																				.getClaim("password")
																				.asString();
        

		// (1) id 값에 해당하는 user 정보 조회
		// (2) user 정보가 존재한다면, password 검증
		// (3) UserDetails 객체 생성 후 시큐리티 세션에 Authentication 저장
		if (
				id != null
			&&	password != null
		) {
			// (1) id 값에 해당하는 user 정보 조회
			UserEntity user = userRepository.findById(id).orElse(null);
			
			if ( user != null ) {
				// (2) user 정보가 존재한다면, password 검증
				if ( bCryptPasswordEncoder.matches(password, user.getPassword()) ){

					// (3) UserDetails 객체 생성 후 시큐리티 세션에 Authentication 저장
					PrincipalDetails principalDetails = new PrincipalDetails(user);
					// Authentication객체를 만들어 준다.
					Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
					// 강제로 시큐리트 세션에 접근하여 Authentication 객체 저장.
					SecurityContextHolder.getContext().setAuthentication(authentication);

				}
			}
		}

		chain.doFilter(request, response);
	}

}
