package com.springboot.jpa.business.service.user;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.springboot.jpa.api.dto.user.UserDeliveryDto;
import com.springboot.jpa.code.user.DeliveryCondition;
import com.springboot.jpa.data.entity.user.UserDeliveryEntity;
import com.springboot.jpa.data.entity.user.UserEntity;
import com.springboot.jpa.data.entity.user.id.UserDeliveryId;
import com.springboot.jpa.data.repository.user.UserDeliveryRepository;
import com.springboot.jpa.data.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDeliveryService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDeliveryRepository userDeliveryRepository;
	
	/**
	 * 배송지 리스트 조회
	 * @param condtion	-- 검색조건
	 * @param type		-- 조건구분 DeliveryCondition.class
	 * @return	UserDto.Info_userDeliverys.class
	 */
	public List<UserDeliveryDto.Info_userDelivery> getUserDeliveries(String condition, DeliveryCondition type) {
		
		List<UserDeliveryEntity> findUserDeliveries = new ArrayList<> ();
		switch	(type) {
			case ALL: 
			findUserDeliveries = userDeliveryRepository.findByUserDeliveryIdIdOrAddressContaining(condition, condition);
			break;
			case ID: 
			findUserDeliveries = userDeliveryRepository.findByUserDeliveryIdIdContaining(condition);
			break;
			case ADDR: 
			findUserDeliveries = userDeliveryRepository.findByAddressContaining(condition);
			break;
		}

		
		List<UserDeliveryDto.Info_userDelivery> info_userDeliveries = new ArrayList<> ();
			findUserDeliveries.stream().forEach(findUserDelivery -> {
				info_userDeliveries.add(
					UserDeliveryDto.Info_userDelivery.builder()
											.id(findUserDelivery.getUserDeliveryId().getId())
											.seq(findUserDelivery.getUserDeliveryId().getSeq())
											.address(findUserDelivery.getAddress())
											.insertYmd(findUserDelivery.getInsertDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
											.build()
				);
			});
		return info_userDeliveries;
	}

	/**
	 * 배송지 단건 조회
	 * @param id	-- 아이디
	 * @param seq	-- 일련번호
	 * @return	UserDto.Info_userDelivery.class
	 */
	public UserDeliveryDto.Info_userDelivery getUserDelivery (String id, Long seq) {

		UserDeliveryEntity findUserDeliveryEntity = userDeliveryRepository.findById(
														UserDeliveryId.builder()
																	.id(id)
																	.seq(seq)
																	.build()
													).orElse(null);
		if( findUserDeliveryEntity==null ) {
			return null;
		}
		return UserDeliveryDto.Info_userDelivery.builder()
										.id(findUserDeliveryEntity.getUserDeliveryId().getId())
										.seq(findUserDeliveryEntity.getUserDeliveryId().getSeq())
										.address(findUserDeliveryEntity.getAddress())
										.insertYmd(findUserDeliveryEntity.getInsertDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
										.build();
	}


	/**
	 * 배송지 정보 생성
	 * @param id		-- 아이디
	 * @param seq		-- 일련번호
	 * @param address	-- 주소
	 * @return	UserDeliveryDto.Info_userDelivery.class (등록한 배송지 정보)
	 */
	@Transactional
	public UserDeliveryDto.Info_userDelivery createUserDelivery(
			String id
		,	String address
	) {
		// 회원정보 조회
		UserEntity findUser = userRepository.findById(id).orElse(null);
		
		// 배송지정보 생성 (비영속)
		UserDeliveryEntity createUserDelivery = UserDeliveryEntity.builder()
																.userDeliveryId(
																	UserDeliveryId.builder()
																					.id(id)
																					.seq(this.getMaxSeq(id) + 1L)
																					.build()
																)
																.address(address)
																.build();
		// 회원정보 연결(연관관계)
		createUserDelivery.changeUser(findUser);
		// 배송지 정보 JPA 엔터티매니저 등록 (영속) ... 트랜잭션 끝나면 DB INSERT
		userDeliveryRepository.save(createUserDelivery);
		
		return UserDeliveryDto.Info_userDelivery.builder()
												.id(createUserDelivery.getUserDeliveryId().getId())
												.seq(createUserDelivery.getUserDeliveryId().getSeq())
												.address(createUserDelivery.getAddress())
												.insertYmd(createUserDelivery.getInsertDt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
												.build();
	}

	/**
	 * 배송지 정보 삭제
	 * @param id	-- 아이디
	 * @param seq	-- 일련번호
	 */
	@Transactional
	public void deleteUserDelivery(String id, Long seq) {
		
		UserDeliveryEntity findUserDelivery =	userDeliveryRepository.findById(
													UserDeliveryId.builder()
														.id(id)
														.seq(seq)
														.build()
												).orElse(null);
		userDeliveryRepository.delete(findUserDelivery);

	}



	
	/**
	 * 아이디에 해당하는 배송지 일련번호 최대값 조회
	 * @param id	-- 아이디
	 * @return	Long	(등록된 배송지가 존재하지 않으면 0)
	 */
	private Long getMaxSeq (String id) {

		Long maxSeq = 0L;

		// Long countUserDelivery = userDeliveryRepository.countByUserDeliveryIdId(id);
		UserDeliveryEntity userDelivery = userDeliveryRepository.findFirstByUserDeliveryIdIdOrderByUserDeliveryIdSeqDesc(id).orElse(null);

		if ( userDelivery!=null ) {
			maxSeq = userDelivery.getUserDeliveryId().getSeq();
		}
		return maxSeq;
	}
}
