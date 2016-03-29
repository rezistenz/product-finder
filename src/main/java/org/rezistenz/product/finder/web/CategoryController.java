package org.rezistenz.product.finder.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@RequestMapping({"","/list"})
	public String list(){
		return "categories/list";
	}
}
