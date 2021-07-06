package com.springboot.jpa.api.controller.user;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.springboot.jpa.api.dto.user.UserDto;
import com.springboot.jpa.business.service.user.UserService;
import com.springboot.jpa.code.common.SuccessCode;
import com.springboot.jpa.config.security.auth.PrincipalDetails;
import com.springboot.jpa.config.security.auth.PrincipalDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Tag(name = "회원 정보")
public class UserController {
    
	@Autowired
	private PrincipalDetailsService principalDetailsService;
	@Autowired
	private UserService userService;

    
	@Operation(
			summary = "로그인 처리"
		,	description = "id, password 정보로 로그인처리를 합니다."
	)
	@ApiResponse(
			responseCode = "201"
		,	description="정상처리"
		,	content=@Content(schema=@Schema(implementation=UserDto.Response_login.class))
	)
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto.Response_login login(@RequestBody @Valid UserDto.Body_login requestDto) {

		// 로그인 정보가 있는지 확인
		PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(requestDto.getId());
        
		if ( principalDetails != null ) {

			// 비밀번호 확인
			if ( userService.isMatch(requestDto.getPassword(), principalDetails.getPassword()) ){

				// 토큰 발급
				UserDto.Info_login info_login = userService.getToken(principalDetails, requestDto.getPassword());

				return	UserDto.Response_login.builder()
							.resultCode(SuccessCode.SECURITY_SUCCESS.getResultCode())
							.resultMessage(SuccessCode.SECURITY_SUCCESS.getResultMessage())
							.data(info_login)
							.build();

			} else {
				return	UserDto.Response_login.builder()
							.resultCode(SuccessCode.SECURITY_MISMATCH_PASSWORD.getResultCode())
							.resultMessage(SuccessCode.SECURITY_MISMATCH_PASSWORD.getResultMessage())
							.build();
			}

		} else {
			return	UserDto.Response_login.builder()
						.resultCode(SuccessCode.SECURITY_NO_INFO.getResultCode())
						.resultMessage(SuccessCode.SECURITY_NO_INFO.getResultMessage())
						.build();
		}

	}

	@Operation(
			summary = "회원 등록"
		,	description = "id, password, name, age 정보로 회원정보를 생성합니다."
	)
	@ApiResponse(
			responseCode = "201"
		,	description = "정상처리"
		,	content = @Content(schema=@Schema(implementation=UserDto.Response_createUser.class))
	)
	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto.Response_createUser createUser(@RequestBody @Valid UserDto.Body_createUser requestDto) {

		// 회원아이디 존재여부
		if ( userService.isNew(requestDto.getId()) ) {
			// 회원정보 생성
			UserDto.Info_createUser info_createUser =	userService.createUser(
																requestDto.getId()
															,	requestDto.getPassword()
															,	requestDto.getName()
															,	requestDto.getAge()
														);
			return	UserDto.Response_createUser.builder()
						.resultCode(SuccessCode.PROCESS_SUCCESS.getResultCode())
						.resultMessage(SuccessCode.PROCESS_SUCCESS.getResultMessage())
						.info_createUser(info_createUser)
						.build();
		} else {
			return	UserDto.Response_createUser.builder()
						.resultCode(SuccessCode.USER_OVERLAP.getResultCode())
						.resultMessage(SuccessCode.USER_OVERLAP.getResultMessage())
						.build();
		}

	}


	@Operation(
			summary = "회원정보 수정"
		,	description = "비밀번호, 나이, 이름, 마케팅정보수신동의여부 정보로 회원정보를 수정합니다."
		,	security = { @SecurityRequirement(name = "bearer-key") }
	)
	@ApiResponse(
			responseCode = "200"
		,	description="정상처리"
		,	content=@Content(schema=@Schema(implementation=UserDto.Response_updateUser.class))
	)
	@PutMapping("/user/{id}")
	public UserDto.Response_updateUser updateUser(
			@Parameter(description = "수정할 id") @PathVariable("id") @NotBlank String id
		,	@RequestBody @Valid UserDto.Body_updateUser requestDto
	) {
		
		// 회원아이디 존재여부
		if ( !userService.isNew(id) ) {
			// 회원정보 수정
			UserDto.Info_updateUser info_updateUser = 	userService.updateUser(
																id
															,	requestDto.getPassword()
															,	requestDto.getAge()
															,	requestDto.getName()
															,	requestDto.getMarketingYn()
														);
			return	UserDto.Response_updateUser.builder()
						.resultCode(SuccessCode.PROCESS_SUCCESS.getResultCode())
						.resultMessage(SuccessCode.PROCESS_SUCCESS.getResultMessage())
						.info_updateUser(info_updateUser)
						.build();
		} else {
			return	UserDto.Response_updateUser.builder()
						.resultCode(SuccessCode.USER_NO_INFO.getResultCode())
						.resultMessage(SuccessCode.USER_NO_INFO.getResultMessage())
						.build();
		}

	}

}
