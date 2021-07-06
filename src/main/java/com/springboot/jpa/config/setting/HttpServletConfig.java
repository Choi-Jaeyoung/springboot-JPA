package com.springboot.jpa.config.setting;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServletConfig {
    
	private static ThreadLocal<ServletContext>      context  = new ThreadLocal<ServletContext>();
	private static ThreadLocal<HttpServletRequest>  request  = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	
	public static void set(HttpServletRequest req, HttpServletResponse res) {
		request.set(req);
		response.set(res);
		context.set(req.getSession().getServletContext());
	}
	
	public static void remove() {
		request.remove();
		response.remove();
		context.remove();
	}

	public static HttpServletRequest getRequest() {
		return request.get();
	}
	
	public static HttpServletResponse getResponse() {
		return response.get();
	}
	
	public static ServletContext getContext() {
		return context.get();
	}
}
