package org.rezistenz.product.finder.web;

import java.util.Collection;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.rezistenz.product.finder.model.Product;
import org.rezistenz.product.finder.service.CategoryService;
import org.rezistenz.product.finder.service.ProductService;
import org.rezistenz.product.finder.web.dto.PagingInfo;
import org.rezistenz.product.finder.web.dto.ProductFilter;
import org.rezistenz.product.finder.web.dto.ProductForm;
import org.rezistenz.product.finder.web.dto.ProductListItem;
import org.rezistenz.product.finder.web.dto.ProductPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {

	public static final int DEFAULT_PAGE_SIZE = 10;
	
	private static Logger log=Logger.getLogger(ProductController.class.getName());
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@RequestMapping({"","/list"})
	public String list(
			ProductFilter productFilter,
			PagingInfo pagingInfo,
			Model model){
		
		log.info(pagingInfo.toString());
		
		if(pagingInfo.getPageSize() == 0){
			pagingInfo.setPageSize(DEFAULT_PAGE_SIZE);
		}
		if(pagingInfo.getOrderDir() == null || pagingInfo.getOrderDir().isEmpty()){
			pagingInfo.setOrderDir("desc");
		}
		if(pagingInfo.getOrderCol() == null || pagingInfo.getOrderCol().isEmpty()){
			pagingInfo.setOrderCol("id");
		}
		
		long productsCount=productService.getProductsCount(productFilter);
		
		pagingInfo.setItemsCount(productsCount);
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		model.addAttribute("productFilter", productFilter);
		model.addAttribute("pagingInfo", pagingInfo);
		
		model.addAttribute("productPagedList", new ProductPagedList(pagingInfo, products));
		
		return "products/list";
	}
	
	@RequestMapping(value={"/create","/edit"}, method=RequestMethod.GET)
	public String edit(
			@RequestParam(value="id", defaultValue="0") int id, 
			Model model){
		log.info("id:"+id);
		
		ProductForm productForm=null;
		
		if(id > 0L){
			Product product=productService.findById(id);
			productForm=new ProductForm(product);
		}else{
			productForm=new ProductForm();
		}
		
		model.addAttribute("productForm",productForm);
		
		model.addAttribute("categories", categoryService.findAll());
		
		return "products/edit";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//binder.setAllowedFields(new String[]{"name"});
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(
			@ModelAttribute("productForm") @Valid ProductForm productForm, 
			BindingResult result,
			Model model){
		
		log.info("result.hasErrors():"+result.hasErrors());
		
		if (result.hasErrors()) {
			result.reject("error.global");
			
			model.addAttribute("categories", categoryService.findAll());
			
			return "products/edit";
		}
		
		productService.save(productForm);
		
		if(productForm.getId() > 0L){
			model.addAttribute("id", productForm.getId());
		}
		
		return "redirect:/products/edit";
	}
}
