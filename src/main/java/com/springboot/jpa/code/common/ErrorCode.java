package com.springboot.jpa.code.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	SECURITY_ERROR(5555, "인증 처리 중 예외가 발생하였습니다."),

	PROCESS_ERROR(4444, "시스템 처리 중 예외가 발생하였습니다."),
	PROCESS_INVALID_PARAMETER(4010, "유효하지 않은 요청 정보입니다."),
	PROCESS_INVALID_FORMAT(4011, "데이터 형식 변환 중 오류가 발생하였습니다.");

	private final int errorCode;
	private final String errorMessage;
        
}
