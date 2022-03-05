package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		
		// CMRespDto, Script 비교
		// 1. 클라이언트한테 응답할 때는 Script가 좋다. (알람창)
		// 2. Ajax 통신 - CMRespDto (data 전달)
		// 3. Android 통신 - CMRespDto (data 전달)
		return Script.back(e.getErrorMap().toString());
	}
}
