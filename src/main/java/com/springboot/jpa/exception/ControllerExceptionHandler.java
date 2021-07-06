package com.springboot.jpa.exception;

import javax.validation.ConstraintViolationException;

import com.springboot.jpa.api.dto.CommonDto;
import com.springboot.jpa.api.dto.CommonDto.Response_default;
import com.springboot.jpa.code.common.ErrorCode;
import com.springboot.jpa.config.setting.HttpServletConfig;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

	// private Logger log = LoggerFactory.getLogger("rolling_error");

	private void logStackPrint(Exception ex) {
		// log.error("Exception : {}", ex.getClass());
		// log.error("Message : {}", ex.getMessage());
		// log.error("Cause : {}", ex.getCause());
        
		// ThreadLocal Remove
		HttpServletConfig.remove();
	}

	@ExceptionHandler(Exception.class)
	protected CommonDto.Response_default handleException(Exception e) {
		this.logStackPrint(e);

		return CommonDto.Response_default.builder()
			.resultCode(ErrorCode.PROCESS_ERROR.getErrorCode())
			.resultMessage(ErrorCode.PROCESS_ERROR.getErrorMessage())
			.build();
	}

	/**
	 * @RequestBody  Validation
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected CommonDto.Response_default handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		this.logStackPrint(e);
        
		return CommonDto.Response_default.builder()
			.resultCode(ErrorCode.PROCESS_INVALID_PARAMETER.getErrorCode())
			.resultMessage(ErrorCode.PROCESS_INVALID_PARAMETER.getErrorMessage())
			.build();
	}

	/**
	 * @PathVariable, @RequestParam  Validation
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected Response_default handleConstraintViolationException(ConstraintViolationException e) {
		this.logStackPrint(e);
        
		return Response_default.builder()
			.resultCode(ErrorCode.PROCESS_INVALID_PARAMETER.getErrorCode())
			.resultMessage(ErrorCode.PROCESS_INVALID_PARAMETER.getErrorMessage())
			.build();
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected Response_default handleInvalidFormatException(HttpMessageNotReadableException e) {
		this.logStackPrint(e);
        
		return Response_default.builder()
			.resultCode(ErrorCode.PROCESS_INVALID_FORMAT.getErrorCode())
			.resultMessage(ErrorCode.PROCESS_INVALID_FORMAT.getErrorMessage())
			.build();
	}
    
}
