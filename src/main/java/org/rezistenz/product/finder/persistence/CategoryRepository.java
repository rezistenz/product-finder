package org.rezistenz.product.finder.persistence;

import java.util.Collection;
import java.util.Map;

import org.rezistenz.product.finder.model.Category;

public interface CategoryRepository extends EnityRepository<Category, Integer> {

	Collection<Category> findByParams(Map<String, Object> params);
	
	Long findByParamsCount(Map<String, Object> params);

	Collection<Category> findAll();

}
