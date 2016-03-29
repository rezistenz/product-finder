package org.rezistenz.product.finder.service;

import java.util.Collection;

import org.rezistenz.product.finder.model.Category;


public interface CategoryService {
	Collection<Category> findAll();
}
