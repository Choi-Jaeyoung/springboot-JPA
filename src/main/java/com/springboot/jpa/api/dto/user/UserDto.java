package com.springboot.jpa.api.dto.user;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.springboot.jpa.code.user.MarketingYn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDto {

    //=== BODYs ======================================================
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "로그인 요청 Body")
	public static class Body_login {
		@Schema(description = "아이디", example = "user001")
		@NotBlank(message = "id is not blank!")
		private String id;

		@Schema(description = "비밀번호", example = "1234")
		@NotBlank(message = "password is not blank!")
		private String password;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "회원정보 생성 Body")
	public static class Body_createUser {
		@Schema(description = "아이디", example = "user002")
		@NotBlank(message = "id is not blank!")
		private String id;

		@Schema(description = "비밀번호", example = "12345")
		@NotBlank(message = "password is not blank!")
		private String password;

		@Schema(description = "나이", example = "12")
		private Long age;

		@Schema(description = "이름", example = "최재영")
		@NotBlank(message = "name is not blank!")
		private String name;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "회원정보 수정 Body")
	public static class Body_updateUser {
		@Schema(description = "비밀번호", example = "12345")
		@NotNull // Null만 체크
		private String password;

		@Schema(description = "나이", example = "12")
		private Long age;

		@Schema(description = "이름", example = "최재영")
		@NotNull // Null만 체크
		private String name;

		@Schema(description = "마케팅정보수신동의여부", example = "Y")
		@NotNull // Null만 체크
		private MarketingYn marketingYn;
	}

	//=== INFOs ======================================================
	@Getter
	@Setter
	@AllArgsConstructor
	@Builder
	public static class Info_login {
		@Schema(description = "인증 토큰")
		private String authorization;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@Builder
	public static class Info_createUser {
		@Schema(description = "생성된 정보 - ID")
		private String id;
		@Schema(description = "생성된 정보 - 이름")
		private String name;
		@Schema(description = "생성된 정보 - 나이")
		private Long age;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@Builder
	public static class Info_updateUser {
		@Schema(description = "수정된 정보 - ID")
		private String id;
		@Schema(description = "수정된 정보 - 이름")
		private String name;
		@Schema(description = "수정된 정보 - 나이")
		private Long age;
	}


	//=== Responses ======================================================
	@Getter
	@AllArgsConstructor
	@Builder
	@Schema(description = "응답 데이터")
	public static class Response_login {
		@Schema(description = "결과코드", defaultValue = "2100")
		private int resultCode;
		@Schema(description = "결과메시지", defaultValue = "로그인 인증 완료되었습니다.")
		private String resultMessage;
		private Info_login data;
	}

	@Getter
	@AllArgsConstructor
	@Builder
	@Schema(description = "회원생성 응답 데이터")
	public static class Response_createUser {
		@Schema(description = "결과코드", defaultValue = "2100")
		private int resultCode;
		@Schema(description = "결과메시지", defaultValue = "회원정보가 생성되었습니다.")
		private String resultMessage;
		private Info_createUser info_createUser;
	}

	@Getter
	@AllArgsConstructor
	@Builder
	@Schema(description = "회원수정 응답 데이터")
	public static class Response_updateUser {
		@Schema(description = "결과코드", defaultValue = "2100")
		private int resultCode;
		@Schema(description = "결과메시지", defaultValue = "회원정보가 수정되었습니다.")
		private String resultMessage;
		private Info_updateUser info_updateUser;
	}

}
