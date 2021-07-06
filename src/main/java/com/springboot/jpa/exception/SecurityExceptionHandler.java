package com.springboot.jpa.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.jpa.api.dto.CommonDto;
import com.springboot.jpa.code.common.ErrorCode;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class SecurityExceptionHandler implements AuthenticationEntryPoint {

	// private Logger log = LoggerFactory.getLogger("rolling_error");

	private void logStackPrint(Exception ex) {
		// log.error("Exception : {}", ex.getClass());
		// log.error("Message : {}", ex.getMessage());
		// log.error("Cause : {}", ex.getCause());
	}

    @Override
    public void commence(
			HttpServletRequest request
		,	HttpServletResponse response
		,	AuthenticationException authException
	) throws IOException {
                
		logStackPrint(authException);
        
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(
			new ObjectMapper().writeValueAsString(
				CommonDto.Response_default.builder()
					.resultCode(ErrorCode.SECURITY_ERROR.getErrorCode())
					.resultMessage(ErrorCode.SECURITY_ERROR.getErrorMessage())
					.build()
			)
		);
	}


}
