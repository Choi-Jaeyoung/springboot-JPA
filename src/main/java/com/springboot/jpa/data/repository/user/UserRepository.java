package com.springboot.jpa.data.repository.user;

import com.springboot.jpa.data.entity.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String>   {
	
}
