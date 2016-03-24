package com.example.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value="/web/index.do")
	public String index(ModelMap model) {
		return "/index.jsp";	
	}
	@RequestMapping(value="/login.do")
	public String userLogin(ModelMap model) {
		return "/login/login.jsp";
	}
	@RequestMapping(value="/logout.do")
	public String logout(ModelMap model) {
		return "redirect:/login.do?logout";
	}
}
