package com.example.web.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@RequestMapping(value="/index.do")
	public String index(ModelMap model,HttpServletRequest request) {

		String myField = (String)request.getSession().getAttribute("myfield");
		System.out.println("Another input Field : " + myField);

		return "/index.jsp";	
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String userLogin(ModelMap model) {
		return "/login/login.jsp";
	}
	
	@RequestMapping(value="/logout.do")
	public String logout(ModelMap model,HttpServletRequest request) {
		request.getSession().removeAttribute("myfield");
		return "redirect:/login.do?logout";
	}
}
