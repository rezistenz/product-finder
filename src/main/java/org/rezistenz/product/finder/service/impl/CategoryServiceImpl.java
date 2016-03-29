package org.rezistenz.product.finder.service.impl;

import java.util.Collection;

import org.rezistenz.product.finder.model.Category;
import org.rezistenz.product.finder.persistence.CategoryRepository;
import org.rezistenz.product.finder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public void setCategoryRepository(CategoryRepository categoryRepository){
		this.categoryRepository=categoryRepository;
	}

	@Override
	@Transactional(readOnly=true)
	public Collection<Category> findAll() {
		return categoryRepository.findAll();
	}

}
