package org.rezistenz.product.finder.web.dto;

import java.math.BigDecimal;

public class ProductListItem {
	
	public ProductListItem(
			Integer id, 
			String name, 
			String categoryName,
			BigDecimal price) {
		super();
		this.id = id;
		this.name = name;
		this.categoryName = categoryName;
		this.price = price;
	}
	
	private Integer id;
	private String name;
	private String categoryName;
	private BigDecimal price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "ProductListItem [id=" + id + ", name=" + name
				+ ", categoryName=" + categoryName 
				+ ", price=" + price + "]";
	}
}
