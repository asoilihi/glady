package com.backend.as.glady.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller redirigeant vers le swagger
 * 
 * @author asoilihi
 *
 */
@Controller
public class MainController {
	@GetMapping(value = "/")
	ModelAndView showSwaggerUI() {
		return new ModelAndView("redirect:swagger-ui/index.html");
	}
}
