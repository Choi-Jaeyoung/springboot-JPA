package com.springboot.jpa.config.security.auth;

import java.util.ArrayList;
import java.util.Collection;

import com.springboot.jpa.data.entity.user.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다. (폼 로그인 시...)
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어줍니다. (Security ContextHolder)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User정보가 있어야 됨.
// User오브젝트타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)
@Data
@AllArgsConstructor
@Builder
public class PrincipalDetails implements UserDetails {

	// User 객체가 따로 있다면.. 
	// 맴버변수로 객체 선언..

	private String id;
	private String password;
	private String role;

	
	public PrincipalDetails(UserEntity user) {
		this.id = user.getId();
		this.password = user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
			collect.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return role;
				}
			}
        );
		return collect;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}