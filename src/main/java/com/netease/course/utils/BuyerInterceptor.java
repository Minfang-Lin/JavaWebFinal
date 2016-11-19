package com.netease.course.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.netease.course.model.User;

public class BuyerInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser == null) {
			response.sendRedirect("/login");
			return false;
		} else if (currentUser.getUsertype() != 0) {
			System.out.println("buyer interceptor");
			response.sendRedirect("/");
			return false;
		}
		return true;
	}

}
