package com.netease.course.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class BasicController {

	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ModelAndView requestMissingServletRequest(MissingServletRequestParameterException ex, HttpSession session) {
		ModelAndView mav = new ModelAndView("error/400");
		ex.printStackTrace();
		return mav;
	}
	
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public ModelAndView methodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpSession session) {
		ModelAndView mav = new ModelAndView("error/405");
		ex.printStackTrace();
		return mav;
	}

}
