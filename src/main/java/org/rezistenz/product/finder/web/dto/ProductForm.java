package org.rezistenz.product.finder.web.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.rezistenz.product.finder.model.Category;
import org.rezistenz.product.finder.model.Product;

public class ProductForm {

	public ProductForm() {
		
	}
	
	public ProductForm(Product product) {
		this.id=product.getId();
		this.name=product.getName();
		this.price=product.getPrice();
		Category category = product.getCategory();
		if(category!=null){
			this.categoryId=category.getId();
		}
	}
	
	private int id;
	
	@NotNull
	@Size(min=1, max=255)
	private String name;
	
	private BigDecimal price;
	
	private int categoryId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
}
