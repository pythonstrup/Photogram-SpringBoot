package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component // 딱히 역할이 없기 때문에
@Aspect
public class ValidationAdvice {
	
	// @ Before: 시작하기 전에 동작
	// @ After: 시작하고 난 후에 동작
	// @ Around: 양쪽에서 동작
	
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		// 예를 들어 UserApiController에서 profile 메소드가 실행될 때
		// proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수
		// profile 함수보다 여기가 먼저 실행됨.
		
		//System.out.println("web api 컨트롤러===================");
		
		// 메소드의 모든 인자들을 받아옴.
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg: args) {
			if(arg instanceof BindingResult) {
				//System.out.println("유효성 검사를 하는 함수입니다.");
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for (FieldError error: bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					
					throw new CustomValidationApiException("유효성 검사 실패", errorMap);
				}
			}
		} 		// 만약 유효성 검사에서 오류가 발생되면 이 밑에 있는 코드는 전부 무효화
		
		return proceedingJoinPoint.proceed(); // profile 함수는 이때 실행됨.
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		//System.out.println("web 컨트롤러======================");
		
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg: args) {
			if(arg instanceof BindingResult) {
				//System.out.println("유효성 검사를 하는 함수입니다.");
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for (FieldError error: bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					
					throw new CustomValidationException("유효성 검사 실패", errorMap);
				}
			}
		}
		
		return proceedingJoinPoint.proceed();
	}
}
