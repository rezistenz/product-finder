package org.rezistenz.product.finder.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"","/home"})
public class HomeController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(){
		return "redirect:products/list";
	}
}
