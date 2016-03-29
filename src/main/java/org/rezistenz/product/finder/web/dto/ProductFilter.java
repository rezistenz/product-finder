package org.rezistenz.product.finder.web.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class ProductFilter {

	private String categoryName;
	private String productName;
	
	@NumberFormat(style = Style.NUMBER, pattern = "0.00")
	private BigDecimal priceFrom;
	@NumberFormat(style = Style.NUMBER, pattern = "0.00")
	private BigDecimal priceTo;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	@Override
	public String toString() {
		return "ProductFilter [categoryName=" + categoryName + ", productName="
				+ productName + ", priceFrom=" + priceFrom + ", priceTo="
				+ priceTo + "]";
	}

}
