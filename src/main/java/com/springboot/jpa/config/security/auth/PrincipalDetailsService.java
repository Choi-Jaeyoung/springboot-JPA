package com.springboot.jpa.config.security.auth;

import com.springboot.jpa.data.entity.user.UserEntity;
import com.springboot.jpa.data.repository.user.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 파라메타 username 과 일치하는 계정정보 조회
		// 있으면 UserDetails 객체 생성 후 리턴
		// 없으면 null
		UserEntity user = userRepository.findById(username).orElse(null);
		if ( user != null ) {
			return PrincipalDetails.builder()
									.id(user.getId())
									.password(user.getPassword())
									.build();
		} else {
			return null;
		}

	}

}
