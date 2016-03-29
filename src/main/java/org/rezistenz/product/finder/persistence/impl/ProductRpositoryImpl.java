package org.rezistenz.product.finder.persistence.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.rezistenz.product.finder.model.Product;
import org.rezistenz.product.finder.persistence.ProductRepository;
import org.rezistenz.product.finder.web.ProductController;
import org.rezistenz.product.finder.web.dto.ProductListItem;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRpositoryImpl implements ProductRepository {

	private static Logger log = Logger.getLogger(ProductRpositoryImpl.class.getName());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Product add(Product entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public Product udpate(Product entity) {
		return entityManager.merge(entity);
	}

	@Override
	public Product findByPK(Integer primaryKey) {
		return entityManager.find(Product.class, primaryKey);
	}

	@Override
	public Collection<ProductListItem> findByParams(Map<String, Object> params) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ProductListItem> criteriaQuery = criteriaBuilder.createQuery(ProductListItem.class);
		
		Root<Product> enityRoot = criteriaQuery.from(Product.class);
		
		Predicate criteria = buildCriteria(criteriaBuilder, enityRoot, params);
		
		Join<Object, Object> categoryJoin = enityRoot.join("category", JoinType.LEFT);
		
		criteriaQuery.multiselect(
				enityRoot.<Long>get("id"),
				enityRoot.<String>get("name"),
				categoryJoin.<String>get("name"),
				enityRoot.<BigDecimal>get("price")
			).where(
				criteria
			);
		
		String orderDir = (String) params.get("order_dir");
		if(orderDir == null){
			orderDir="desc";
		}
		String orderCol = (String) params.get("order_col");
		if(orderCol == null){
			orderCol="id";
		}
		
		
		Path<Object> col = null;
		if(orderCol.equals("category_name")){
			col=categoryJoin.get("name");
		}else{
			col=enityRoot.get(orderCol);
		}
			
		if(orderDir.equals("asc")){
			criteriaQuery.orderBy(criteriaBuilder.asc(col));
		}else if(orderDir.equals("desc")){
			criteriaQuery.orderBy(criteriaBuilder.desc(col));
		}
		
		
		TypedQuery<ProductListItem> query = entityManager.createQuery(criteriaQuery);
		
		Integer pageSize=(Integer) params.get("page_size");
		Integer pageIndex=(Integer) params.get("page_index");
		
		if(pageSize!=null && pageIndex!=null){
			query.setMaxResults(pageSize);
			query.setFirstResult(pageIndex*pageSize);
		}
		
		return query.getResultList();
	}
	
	private Predicate buildCriteria(CriteriaBuilder criteriaBuilder,
			Root<Product> enityRoot, Map<String, Object> params) {
		String categoryName = (String) params.get("category_name");
		
		Predicate criteria = criteriaBuilder.conjunction();
		
		if( StringUtils.isNotBlank(categoryName) ){
			//find with start string ignore cases
			criteria.getExpressions().add(
					criteriaBuilder.like(
							criteriaBuilder.lower(
									enityRoot.join("category", JoinType.LEFT).<String>get("name")
								), 
							categoryName.toLowerCase()+"%"
						)
				);
		}
		
		String productName = (String) params.get("product_name");
		if( StringUtils.isNotBlank(productName) ){
			//find with start string ignore cases
			criteria.getExpressions().add(
					criteriaBuilder.like(
							criteriaBuilder.lower(
									enityRoot.<String>get("name")
								), 
							productName.toLowerCase()+"%"
						)
				);
		}
		
		BigDecimal priceFrom = (BigDecimal) params.get("price_from");
		
		if( priceFrom != null ){
			criteria.getExpressions().add(
						criteriaBuilder.ge(
								enityRoot.<BigDecimal>get("price"),
								priceFrom
							)
					);
		}
	
		BigDecimal priceTo = (BigDecimal) params.get("price_to");
		
		if( priceTo != null ){
			criteria.getExpressions().add(
						criteriaBuilder.le(
								enityRoot.<BigDecimal>get("price"),
								priceTo
							)
					);
		}
		
		return criteria;
	}

	@Override
	public Long findByParamsCount(Map<String, Object> params) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<Product> enityRoot = criteriaQuery.from(Product.class);
		
		Predicate criteria = buildCriteria(criteriaBuilder, enityRoot, params);
		
		
		criteriaQuery.select(criteriaBuilder.count(enityRoot)).where(
				criteria
			);
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		
		return query.getSingleResult();
	}

	
}
