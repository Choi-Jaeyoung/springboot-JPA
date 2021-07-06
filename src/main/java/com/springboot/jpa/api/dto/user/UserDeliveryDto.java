package com.springboot.jpa.api.dto.user;

import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDeliveryDto {
    
    //=== BODYs ======================================================

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "배송지정보 생성 Body")
	public static class Body_createUserDelivery {
		@Schema(description = "아이디", example = "user002")
		@NotBlank(message = "id is not blank!")
		private String id;

		@Schema(description = "주소", example = "판교역로 225-18")
		@NotBlank(message = "password is not blank!")
		private String address;

	}


	//=== INFOs ======================================================

	@Getter
	@Setter
	@AllArgsConstructor
	@Builder
	public static class Info_userDelivery {
		@Schema(description = "배송지 - ID")
		private String id;
		@Schema(description = "배송지 - SEQ")
		private Long seq;
		@Schema(description = "배송지 - 주소")
		private String address;
		@Schema(description = "배송지 - 등록일")
		private String insertYmd;
	}


	//=== Responses ======================================================

	@Getter
	@AllArgsConstructor
	@Builder
	@Schema(description = "배송지 리스트 조회 응답 데이터")
	public static class Response_getUserDeliveries {
		@Schema(description = "결과코드", defaultValue = "2100")
		private int resultCode;
		@Schema(description = "결과메시지", defaultValue = "정보가 조회되었습니다.")
		private String resultMessage;
		private List<Info_userDelivery> userDeliveries;
	}
	
	@Getter
	@AllArgsConstructor
	@Builder
	@Schema(description = "배송지 단건 조회 응답 데이터")
	public static class Response_getUserDelivery {
		@Schema(description = "결과코드", defaultValue = "2100")
		private int resultCode;
		@Schema(description = "결과메시지", defaultValue = "정보가 조회되었습니다.")
		private String resultMessage;
		private Info_userDelivery userDelivery;
	}

	@Getter
	@AllArgsConstructor
	@Builder
	@Schema(description = "배송지 등록 응답 데이터")
	public static class Response_createUserDelivery {
		@Schema(description = "결과코드", defaultValue = "2100")
		private int resultCode;
		@Schema(description = "결과메시지", defaultValue = "정보가 생성되었습니다.")
		private String resultMessage;
		private Info_userDelivery userDelivery;
	}

	@Getter
	@AllArgsConstructor
	@Builder
	@Schema(description = "배송지 삭제 응답 데이터")
	public static class Response_deleteUserDelivery {
		@Schema(description = "결과코드", defaultValue = "2100")
		private int resultCode;
		@Schema(description = "결과메시지", defaultValue = "정보가 삭제되었습니다.")
		private String resultMessage;
	}
    
}
