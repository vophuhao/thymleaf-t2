package vn.iostart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class crudCategory {

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String requestMethodName(@RequestParam String param) {
		return "/list";
	}
	
	
}
