package com.green.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	// @RequestMapping : @GetMapping + @PostMapping
	@RequestMapping("/hi")
	public String hi(Model model) {
		model.addAttribute("ㅁㅁ","그래");
		return "greetings"; // greetings.mustache
	}
	
	@GetMapping("/hi2") 
	public String hi2(Model model) {
		model.addAttribute("name","로제");
		return "greetings2"; // greetings2.mustache
	}
	
	@GetMapping("/hi3") 
	public String hi3(String phone, Model model) {
		model.addAttribute("name","로제");
		return "greetings3"; // greetings3.mustache
	}
	
	@GetMapping("/hi4") 
	public String hi4(Model model) {
		model.addAttribute("name","장원영");
		return "greetings4"; // greetings3.mustache
	}
	

}
