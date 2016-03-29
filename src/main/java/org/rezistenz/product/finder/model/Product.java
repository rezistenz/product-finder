package org.rezistenz.product.finder.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PROD")
public class Product {
	
	@TableGenerator(
			name="product_gen",
			table="SEQUENCES",
			pkColumnName="NAME",
			valueColumnName="VALUE",
			pkColumnValue="PROD",
			initialValue=1000
		)
	@Id
	@GeneratedValue(
		strategy = GenerationType.TABLE, 
		generator="product_gen"
	)
	@Column(name="ID")
	private Integer id;
	
	@NotNull
	@Column(name="NAME", length=255)
	private String name;
	
	@Column(name="PRICE",columnDefinition="NUMERIC(16,2)")
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name="CAT_ID")
	private Category category;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price
				+ ", category=" + category + "]";
	}
	
}
