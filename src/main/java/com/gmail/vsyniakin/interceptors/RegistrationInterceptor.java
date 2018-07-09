package com.gmail.vsyniakin.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		if (!email.isEmpty() || !login.isEmpty() || !password.isEmpty()) {
			return super.preHandle(request, response, handler);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
