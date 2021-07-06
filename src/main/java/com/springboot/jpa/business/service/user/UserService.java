package com.springboot.jpa.business.service.user;

import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springboot.jpa.api.dto.user.UserDto;
import com.springboot.jpa.code.user.MarketingYn;
import com.springboot.jpa.config.security.auth.PrincipalDetails;
import com.springboot.jpa.config.security.jwt.JWTProperties;
import com.springboot.jpa.config.setting.HttpServletConfig;
import com.springboot.jpa.data.entity.user.UserDetailEntity;
import com.springboot.jpa.data.entity.user.UserEntity;
import com.springboot.jpa.data.repository.user.UserDetailRepository;
import com.springboot.jpa.data.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailRepository userDetailRepository;

	/**
	 * 토큰 발급
	 * @param principalDetails
	 * @return UserDto.Info_login.class
	 */
	public UserDto.Info_login getToken(PrincipalDetails principalDetails, String rawPassword) {

		String jwtToken = JWT.create()
							.withSubject("인증토큰")
							.withExpiresAt(new Date(System.currentTimeMillis() + JWTProperties.EXPIRATION_TIME))
							.withClaim("id", principalDetails.getUsername())
							.withClaim("password", rawPassword) // 암호화 안된 비밀번호를 넣어야 나중에 토큰인증할때 ....
							.sign(Algorithm.HMAC512(JWTProperties.SECRET));

		HttpServletConfig.getResponse().addHeader("Authorization", JWTProperties.TOKEN_PRIFIX + jwtToken);

		return UserDto.Info_login.builder()
		.authorization(JWTProperties.TOKEN_PRIFIX + jwtToken)
		.build();
	}

	/**
	 * 비밀번호 일치 여부
	 * @param targetPasswaord -- 대상
	 * @param comparingPassword -- 비교값
	 * @return Boolean
	 */
	public Boolean isMatch(String targetPasswaord, String comparingPassword) {
		return bCryptPasswordEncoder.matches(targetPasswaord, comparingPassword);
	}

	/**
	 * 회원아이디 존재여부
	 * @param id -- 중복검사 대상
	 * @return	Boolean
	 */
	@Transactional
	public Boolean isNew(String id){
		UserEntity user = userRepository.findById(id).orElse(null);
		return (user == null);
	}

	/**
	 * 회원정보 등록
	 * @param id	-- 등록할 아이디
	 * @param password -- 등록할 비밀번호
	 * @param name -- 등록할 이름
	 * @param age -- 등록할 나이
	 * @return	UserDto.Info_createUser.class
	 */
	@Transactional
	public UserDto.Info_createUser createUser(
			String id
		,	String password
		,	String name
		,	Long age
	) {
		
		UserDetailEntity userDetail = UserDetailEntity.builder()
														.id(id)
														.password(bCryptPasswordEncoder.encode(password))
														.name(name)
														.age(age)
														.build();
		userDetailRepository.save(userDetail);

		return UserDto.Info_createUser.builder()
										.id(userDetail.getId())
										.name(userDetail.getName())
										.age(userDetail.getAge())
										.build();
	}


	/**
	 * 회원정보 수정
	 * @param password	-- 수정할 비밀번호
	 * @param age	-- 수정할 나이
	 * @param name	-- 수정할 이름
	 * @param marketingYn	-- 수정할 마케팅정보수신동의여부 MarketingYn.class
	 * @return	UserDto.Info_updateUser.class
	 */
	@Transactional
	public UserDto.Info_updateUser updateUser(
			String id
		,	String password
		,	Long age
		,	String name
		,	MarketingYn marketingYn
	) {
		UserDetailEntity userDetail = userDetailRepository.findById(id).orElse(null);


		if ( !password.isBlank() ) {
			userDetail.setPassword(bCryptPasswordEncoder.encode(password));
		}
		if ( age!=null ) {
			userDetail.setAge(age);
		}
		if ( !name.isBlank() ) {
			userDetail.setName(name);
		}
		if ( MarketingYn.Y.equals(marketingYn) ) {
			userDetail.setMarketingDt(LocalDateTime.now());
		} else {
			userDetail.setMarketingDt(null);
		}
		userDetail.setMarketingYn(marketingYn);

		return UserDto.Info_updateUser.builder()
										.id(id)
										.age(age)
										.name(name)
										.build();

	}


}
