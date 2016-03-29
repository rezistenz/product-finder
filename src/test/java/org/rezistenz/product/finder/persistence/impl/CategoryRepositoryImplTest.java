package org.rezistenz.product.finder.persistence.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rezistenz.product.finder.model.Category;
import org.rezistenz.product.finder.persistence.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/META-INF/applicationContextService.xml"})
@Transactional
public class CategoryRepositoryImplTest {

	private static final int DEFAULT_CATEGORIES_COUNT = 5;
	
	private static Logger log=Logger.getLogger(CategoryRepositoryImplTest.class.getName());
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testAdd() {
		Category category=new Category();
		
		String added_cat_name = "added_cat_name";
		
		category.setName(added_cat_name);

		categoryRepository.add(category);

		em.flush();

		assertNotNull(category);
		assertTrue(category.getId()>0);

		log.info(""+category.getId());

		Category categoryInDB=categoryRepository.findByPK(category.getId());

		assertNotNull(categoryInDB);
		assertTrue(categoryInDB.getName().equals(added_cat_name));
	}

	@Test
	public void testUdpate() {
		Category category=new Category();
		
		String category_for_update_name = "category_for_update_name";
		
		category.setName(category_for_update_name);

		categoryRepository.add(category);

		em.flush();

		Category categoryForUpdate=categoryRepository.findByPK(category.getId());

		assertTrue(categoryForUpdate.getName().equals(category_for_update_name));

		String updated_category_name = "updated_category_name";
		
		categoryForUpdate.setName(updated_category_name);

		categoryRepository.udpate(categoryForUpdate);

		em.flush();

		Category categoryUpdated=categoryRepository.findByPK(category.getId());
		assertNotNull(categoryUpdated);
		assertTrue(categoryUpdated.getName().equals(updated_category_name));
	}

	@Test
	public void testFindByPK() {
		Category category=new Category();
		
		String category_for_find_name = "category_for_find_name";
		
		category.setName(category_for_find_name);

		categoryRepository.add(category);

		em.flush();

		Category categoryFinded=categoryRepository.findByPK(category.getId());
		assertNotNull(categoryFinded);
		assertTrue(categoryFinded.getName().equals(category_for_find_name));
	}

	@Test
	public void testFindByParamsEmpty(){
		Map<String, Object> params=new HashMap<String, Object>();

		Collection<Category> items=categoryRepository.findByParams(params);

		log.info("products.size():"+items.size());

		for (Category item : items) {
			log.info(item.toString());
		}

		assertFalse(items.isEmpty());
		assertTrue(items.size() == DEFAULT_CATEGORIES_COUNT);
	}

	@Test
	public void testFindByParamsNotEmpty(){

		Category category=new Category();
		String category_4_name = "category_4_name";

		category.setName(category_4_name);

		categoryRepository.add(category);


		Map<String, Object> params=new HashMap<String, Object>();

		params.put("category_name", category_4_name);

		Collection<Category> items=categoryRepository.findByParams(params);

		log.info("products.size():"+items.size());

		for (Category item : items) {
			log.info(item.toString());
		}

		assertFalse(items.isEmpty());
		assertTrue(items.size() == 1);

		Category firstFoundedCategory=items.iterator().next();

		assertTrue(firstFoundedCategory.getName().equals(category_4_name));
	}

}
