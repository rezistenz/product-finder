package org.rezistenz.product.finder.persistence.impl;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.rezistenz.product.finder.model.Category;
import org.rezistenz.product.finder.persistence.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Category add(Category entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public Category udpate(Category entity) {
		return entityManager.merge(entity);
	}

	@Override
	public Category findByPK(Integer primaryKey) {
		return entityManager.find(Category.class, primaryKey);
	}

	@Override
	public Long findByParamsCount(Map<String, Object> params) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<Category> enityRoot = criteriaQuery.from(Category.class);
		
		Predicate criteria = buildCriteria(criteriaBuilder, enityRoot, params);
		
		
		criteriaQuery.select(criteriaBuilder.count(enityRoot)).where(
				criteria
			);
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
		
		return query.getSingleResult();
	}

	private Predicate buildCriteria(CriteriaBuilder criteriaBuilder,
			Root<Category> enityRoot, Map<String, Object> params) {
		Predicate criteria = criteriaBuilder.conjunction();
		
		String categoryName = (String) params.get("category_name");
		
		if( categoryName!=null && !categoryName.isEmpty()){
			criteria.getExpressions().add(
					criteriaBuilder.like(
							criteriaBuilder.lower(
									enityRoot.<String>get("name")
								), 
							"%"+categoryName.toLowerCase()+"%"
						)
				);
		}
		return criteria;
	}
	
	@Override
	public Collection<Category> findByParams(Map<String, Object> params) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
		
		Root<Category> enityRoot = criteriaQuery.from(Category.class);
		
		
		Predicate criteria = buildCriteria(criteriaBuilder, enityRoot, params);
		
		
		criteriaQuery.select(enityRoot).where(
				criteria
			);
		
		TypedQuery<Category> query = entityManager.createQuery(criteriaQuery);
		
		Integer pageSize=(Integer) params.get("page_size");
		Integer pageIndex=(Integer) params.get("page_index");
		
		if(pageSize!=null && pageIndex!=null){
			query.setMaxResults(pageSize);
			query.setFirstResult(pageIndex*pageSize);
		}
		
		return query.getResultList();
	}

	@Override
	public Collection<Category> findAll() {
		return entityManager.createNamedQuery("findAll", Category.class)
				.getResultList();
	}
}
