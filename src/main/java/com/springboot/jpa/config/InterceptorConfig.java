package com.springboot.jpa.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springboot.jpa.config.setting.HttpServletConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements HandlerInterceptor, WebMvcConfigurer {

	@Override
	public boolean preHandle(
			HttpServletRequest request
		,	HttpServletResponse response
		,	Object handler
	)throws Exception {
		System.out.println("=================== INTERCEPTOR preHandle ====================");
        
		// ThreadLocal Set
		HttpServletConfig.set(request, response);
        
		return true;
    }

	@Override
	public void postHandle(
			HttpServletRequest request
		,	HttpServletResponse response
		,	Object handler
		,	ModelAndView modelAndView
	) throws Exception {
		System.out.println("=================== INTERCEPTOR postHandle ====================");

		// ThreadLocal Remove
		HttpServletConfig.remove();

		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("=================== INTERCEPTOR addInterceptors ====================");
		registry.addInterceptor(new InterceptorConfig())
				.addPathPatterns("/**")
				.excludePathPatterns("/api-docs/**")
				.excludePathPatterns("/swagger-ui/**");
	}
   
}