package com.springboot.jpa.config.security.jwt;

public interface JWTProperties {
	String SECRET = "JNICK";
	// Long EXPIRATION_TIME = ((1000*60*60)*24)*180L; // (1/1000초) 180일
	Long EXPIRATION_TIME = (1000*60)*30L; // 테스트용 30분
	String TOKEN_PRIFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
    
}
