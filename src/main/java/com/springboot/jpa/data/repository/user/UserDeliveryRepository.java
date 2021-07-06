package com.springboot.jpa.data.repository.user;

import java.util.List;
import java.util.Optional;

import com.springboot.jpa.data.entity.user.UserDeliveryEntity;
import com.springboot.jpa.data.entity.user.id.UserDeliveryId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeliveryRepository extends JpaRepository<UserDeliveryEntity, UserDeliveryId>  {
	
    public List<UserDeliveryEntity> findByAddressContaining(String address);
    public List<UserDeliveryEntity> findByUserDeliveryIdIdContaining(String id);
    public List<UserDeliveryEntity> findByUserDeliveryIdIdOrAddressContaining(String id, String address);

    public Long countByUserDeliveryIdId(String id);
    public Optional<UserDeliveryEntity> findFirstByUserDeliveryIdIdOrderByUserDeliveryIdSeqDesc(String id);

}
