package com.springboot.jpa.code.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    
	SECURITY_SUCCESS(2100, "로그인 인증 완료되었습니다."),
	SECURITY_NO_INFO(2101, "존재하지 않은 로그인 정보입니다."),
	SECURITY_MISMATCH_PASSWORD(2102, "비밀번호가 일치하지 않습니다."),
    
	PROCESS_SUCCESS(2000, "정상 처리되었습니다."),
	PROCESS_NO_CONTENT(2010, "컨텐츠가 존재하지 않습니다."),
	
	USER_OVERLAP(2091, "중복된 아이디가 존재합니다."),
	USER_NO_INFO(2093, "회원정보가 존재하지 않습니다.");

	private final Integer resultCode;
	private final String resultMessage;
}
