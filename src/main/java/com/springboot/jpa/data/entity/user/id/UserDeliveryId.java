package com.springboot.jpa.data.entity.user.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode(of={"id", "seq"})
@Builder
public class UserDeliveryId implements Serializable {

	private String id;
	private Long seq;

	// @Override
	// public boolean equals(Object o){
	// 	if (o instanceof UserDeliveryId) {
	// 		return	((this.getId()).equals(((UserDeliveryId)o).getId())) && (this.getSeq() == ((UserDeliveryId)o).getSeq());
	// 	} else {
	// 		return false;
	// 	}
	// }

	// @Override
	// public int hashCode() {
	// 	List<Integer> listOfIntegers = (this.getId()).chars()                      
	// 		.boxed()                      
	// 		.collect(Collectors.toList());

	// 	String codeStr = "";
	// 	for(int i:listOfIntegers) {
	// 		codeStr += String.valueOf(i);
	// 	}

	// 	return Integer.parseInt(codeStr) + this.getSeq();
	// }
}
