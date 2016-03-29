package org.rezistenz.product.finder.persistence;

import java.util.Collection;
import java.util.Map;

import org.rezistenz.product.finder.model.Product;
import org.rezistenz.product.finder.web.dto.ProductListItem;

public interface ProductRepository extends EnityRepository<Product, Integer> {
	Collection<ProductListItem> findByParams(Map<String, Object> params);
	Long findByParamsCount(Map<String, Object> params);
}
