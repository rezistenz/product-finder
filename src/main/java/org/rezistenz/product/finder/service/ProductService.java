package org.rezistenz.product.finder.service;

import java.util.Collection;

import org.rezistenz.product.finder.model.Product;
import org.rezistenz.product.finder.web.dto.PagingInfo;
import org.rezistenz.product.finder.web.dto.ProductFilter;
import org.rezistenz.product.finder.web.dto.ProductForm;
import org.rezistenz.product.finder.web.dto.ProductListItem;


public interface ProductService {
	Collection<ProductListItem> findProducts(ProductFilter productFilter,
			PagingInfo pagingInfo);
	long getProductsCount(ProductFilter productFilter);
	Product findById(int id);
	void save(ProductForm productForm);
}
