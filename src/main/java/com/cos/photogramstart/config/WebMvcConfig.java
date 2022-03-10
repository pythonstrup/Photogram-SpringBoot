package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{ // web 설정 파일

	@Value("${file.path}")
	private String uploadFolder;
	
	// /upload/**의 패턴이 발견되면 yml에서 아래주소를 가져와 넣어줌.
	// D:/bigbell/sts-workspace/upload/
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			registry
				.addResourceHandler("/upload/**") // jsp 페이지에서 /upload/** 이런 주소 패턴이 나오면 발동
				.addResourceLocations("file:///" + uploadFolder)
				.setCachePeriod(60*10*6) // 1시간동안 캐싱
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
		}
}
