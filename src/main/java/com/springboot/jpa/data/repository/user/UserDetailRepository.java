package com.springboot.jpa.data.repository.user;

import com.springboot.jpa.data.entity.user.UserDetailEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, String>   {
	
}
