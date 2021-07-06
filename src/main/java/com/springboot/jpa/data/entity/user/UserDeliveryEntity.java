package com.springboot.jpa.data.entity.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.springboot.jpa.data.entity.user.id.UserDeliveryId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="USER_DELIVERY")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDeliveryEntity {

	@EmbeddedId // 복합키
	private UserDeliveryId userDeliveryId;
	@Column(name="ADDRESS")
	private String address;

	@Column(name="INSERTDT", updatable = false)
	@Builder.Default
	private LocalDateTime insertDt = LocalDateTime.now();


	@MapsId("id")	// UserDeliveryId.id (부모테이블 FK + 식별관계)
	@ManyToOne(fetch = FetchType.LAZY) // 지연로딩 (객체 접근시 SELECT 조회)
	@JoinColumn(name = "ID") // 관계주인.. 등록,수정,조회 가능
	private UserEntity user;
	
	// 주인 노예 동시변경
	public void changeUser(UserEntity user) { 
		this.user = user;
		user.getUserDeliverys().add(this);
	}



}