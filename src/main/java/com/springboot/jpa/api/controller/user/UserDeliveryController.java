package com.springboot.jpa.api.controller.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.springboot.jpa.api.dto.user.UserDeliveryDto;
import com.springboot.jpa.business.service.user.UserDeliveryService;
import com.springboot.jpa.business.service.user.UserService;
import com.springboot.jpa.code.common.SuccessCode;
import com.springboot.jpa.code.user.DeliveryCondition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "회원_배송지 정보")
public class UserDeliveryController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDeliveryService userDeliveryService;


	@Operation(
			summary = "배송지 리스트 조회"
		,	description = "아이디 or 주소 정보로 배송지정보를 조회합니다."
		,	security = { @SecurityRequirement(name = "bearer-key") }
	)
	@ApiResponse(
			responseCode = "200"
		,	description="정상처리"
		,	content=@Content(schema=@Schema(implementation=UserDeliveryDto.Response_getUserDeliveries.class))
	)
	@GetMapping("/userDeliverys")
	public UserDeliveryDto.Response_getUserDeliveries getUserDeliverys(
			@Parameter(description = "검색조건 (아이디 or 주소)") @RequestParam(value = "condition", required = false, defaultValue = "") String condition
		,	@Parameter(description = "조건구분 (전체 or 아이디 or 주소)") @RequestParam(value = "type", required = false, defaultValue = "ALL") DeliveryCondition type
	) {

		// 배송지 리스트 조회
		List<UserDeliveryDto.Info_userDelivery> info_userDeliveries = userDeliveryService.getUserDeliveries(condition, type);

		return	UserDeliveryDto.Response_getUserDeliveries.builder()
					.resultCode(SuccessCode.PROCESS_SUCCESS.getResultCode())
					.resultMessage(SuccessCode.PROCESS_SUCCESS.getResultMessage())
					.userDeliveries(info_userDeliveries)
					.build();
	}

	
	@Operation(
			summary = "배송지 단건 조회"
		,	description = "아이디와 SEQ 정보로 배송지정보를 조회합니다."
		,	security = { @SecurityRequirement(name = "bearer-key") }
	)
	@ApiResponse(
			responseCode = "200"
		,	description = "정상처리"
		,	content = @Content(schema=@Schema(implementation=UserDeliveryDto.Response_getUserDelivery.class))
	)
	@GetMapping("/userDelivery/{id}/{seq}")
	public UserDeliveryDto.Response_getUserDelivery getUserDelivery(
			@Parameter(description = "아이디") @PathVariable("id") String id
		,	@Parameter(description = "SEQ") @PathVariable("seq") Long seq
	) {

		// 배송지 단건 조회
		UserDeliveryDto.Info_userDelivery info_userDelivery = userDeliveryService.getUserDelivery(id, seq);
		
		if ( info_userDelivery==null ) {
			return	UserDeliveryDto.Response_getUserDelivery.builder()
						.resultCode(SuccessCode.PROCESS_NO_CONTENT.getResultCode())
						.resultMessage(SuccessCode.PROCESS_NO_CONTENT.getResultMessage())
						.build();
		} else {
			return	UserDeliveryDto.Response_getUserDelivery.builder()
						.resultCode(SuccessCode.PROCESS_SUCCESS.getResultCode())
						.resultMessage(SuccessCode.PROCESS_SUCCESS.getResultMessage())
						.userDelivery(info_userDelivery)
						.build();
		}
	}

	
	@Operation(
			summary = "배송지 등록"
		,	description = "배송지정보를 생성합니다."
		,	security = { @SecurityRequirement(name = "bearer-key") }
	)
	@ApiResponse(
			responseCode = "201"
		,	description="정상처리"
		,	content=@Content(schema=@Schema(implementation=UserDeliveryDto.Response_createUserDelivery.class))
	)
	@PostMapping("/userDelivery")
    @ResponseStatus(HttpStatus.CREATED)
	public UserDeliveryDto.Response_createUserDelivery createUserDelivery(
		@RequestBody @Valid UserDeliveryDto.Body_createUserDelivery requestDto
	) {

		// 아이디 존재 여부
		if( !userService.isNew(requestDto.getId()) ) {

			// 배송지 정보 등록
			UserDeliveryDto.Info_userDelivery info_userDelivery =	userDeliveryService.createUserDelivery(
																			requestDto.getId()
																		,	requestDto.getAddress()
																	);

			return	UserDeliveryDto.Response_createUserDelivery.builder()
						.resultCode(SuccessCode.PROCESS_SUCCESS.getResultCode())
						.resultMessage(SuccessCode.PROCESS_SUCCESS.getResultMessage())
						.userDelivery(info_userDelivery)
						.build();
		} else {
			return	UserDeliveryDto.Response_createUserDelivery.builder()
						.resultCode(SuccessCode.USER_NO_INFO.getResultCode())
						.resultMessage(SuccessCode.USER_NO_INFO.getResultMessage())
						.build();
		}

	}

	
	@Operation(
			summary = "배송지 삭제"
		,	description = "배송지정보를 삭제합니다."
		,	security = { @SecurityRequirement(name = "bearer-key") }
	)
	@ApiResponse(
			responseCode = "200"
		,	description="정상처리"
		,	content=@Content(schema=@Schema(implementation=UserDeliveryDto.Response_deleteUserDelivery.class))
	)
	@DeleteMapping("/userDelivery/{id}/{seq}")
	public UserDeliveryDto.Response_deleteUserDelivery deleteUserDelivery(
			@Parameter(description = "아이디") @PathVariable("id") @NotBlank String id
		,	@Parameter(description = "SEQ") @PathVariable("seq") @NotBlank Long seq
	) {
		
		// 아이디 존재 여부
		if( !userService.isNew(id) ) {

			// 배송지 정보 삭제
			userDeliveryService.deleteUserDelivery(id, seq);
			
			return	UserDeliveryDto.Response_deleteUserDelivery.builder()
						.resultCode(SuccessCode.PROCESS_SUCCESS.getResultCode())
						.resultMessage(SuccessCode.PROCESS_SUCCESS.getResultMessage())
						.build();

		} else {
			return 	UserDeliveryDto.Response_deleteUserDelivery.builder()
						.resultCode(SuccessCode.USER_NO_INFO.getResultCode())
						.resultMessage(SuccessCode.USER_NO_INFO.getResultMessage())
						.build();
		}

	}

}
