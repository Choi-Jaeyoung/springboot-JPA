package com.springboot.jpa.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CommonDto {
    
	@Getter
	@AllArgsConstructor
	@Builder
	@ToString
	@Schema(description = "응답 데이터")
	public static class Response_default {
		@Schema(description = "결과코드")
		private int resultCode;
		@Schema(description = "결과메시지")
		private String resultMessage;
		@Schema(description = "결과추가정보")
		private Object data;
	}
    
}
