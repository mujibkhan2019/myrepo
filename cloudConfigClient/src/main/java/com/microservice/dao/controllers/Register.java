package com.microservice.dao.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.microservice.dao.UserDao;
import com.microservice.entities.User;
import com.microservice.validators.MailValidator;

@Controller
@RequestMapping("/userapp")
public class Register {
	@Autowired
	private UserDao dao;
	@Autowired
	MailValidator validator;
	
	@RequestMapping("/")
	public ModelAndView home()
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("user",new User());
		return mav;
	}
	
	@RequestMapping("/register")
	public ModelAndView save(
		@ModelAttribute	@Valid User user,
		BindingResult br) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		//custom validations are applied
		validator.validate(user, br);
		if(br.hasErrors())
		{
			mav.addObject("user",user);
			mav.setViewName("register");
		}
		else
		{
		dao.save(user);
		mav.setViewName("registered");
		}
		return mav;
	}
}
