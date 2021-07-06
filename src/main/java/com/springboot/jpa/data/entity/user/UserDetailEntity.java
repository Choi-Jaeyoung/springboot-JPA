package com.springboot.jpa.data.entity.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.springboot.jpa.code.user.MarketingYn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="USER_DETAIL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserDetailEntity extends UserEntity {

	@Enumerated(EnumType.STRING) // 문자열 자체가 저장 (EnumType.ORDINAL : 순서값 1, 2 ...)
	@Column(name="MARKETINGYN")
	@Builder.Default
	private MarketingYn marketingYn = MarketingYn.N;

	@Column(name="MARKETINGDT")
	private LocalDateTime marketingDt;

}
