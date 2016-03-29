package org.rezistenz.product.finder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="CAT")
@NamedQuery(
		name="findAll",
		query="select c from Category c"
	)
public class Category {
	
	@TableGenerator(
			name="category_gen",
			table="SEQUENCES",
			pkColumnName="NAME",
			valueColumnName="VALUE",
			pkColumnValue="CAT",
			initialValue=1000
		)
	@Id
	@GeneratedValue(
		strategy = GenerationType.TABLE, 
		generator="category_gen"
	)
	@Column(name="ID")
	private Integer id;
	
	@NotNull
	@Column(name="NAME", length=255)
	private String name;
	
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

}
