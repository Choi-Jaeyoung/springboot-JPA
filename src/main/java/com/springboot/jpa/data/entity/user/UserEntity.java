package com.springboot.jpa.data.entity.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="USER")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder // 상속했을때 Builder 패턴 사용 (** 자식도 @SuperBuilder)
@Inheritance(strategy = InheritanceType.JOINED) // 슈퍼타입 서브타입 1대1 연관관계 (별도 테이블 전략)
public class UserEntity {

	@Id
	@Column(name="ID")
	private String id;

	@Column(name="PASSWORD")
	private String password;
	@Column(name="NAME")
	private String name;
	@Column(name="AGE")
	private Long age;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Builder.Default
	private List<UserDeliveryEntity> userDeliverys = new ArrayList<>();


}
