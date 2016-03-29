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
import org.rezistenz.product.finder.model.Product;
import org.rezistenz.product.finder.persistence.CategoryRepository;
import org.rezistenz.product.finder.persistence.ProductRepository;
import org.rezistenz.product.finder.web.dto.ProductListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/META-INF/applicationContextService.xml"})
@Transactional
public class ProductRpositoryImplTest {

	private static final int DEFAULT_PRODUCTS_COUNT = 7;

	private static Logger log=Logger.getLogger(ProductRpositoryImplTest.class.getName());

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@PersistenceContext
	private EntityManager em;

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testAdd() {
		Product product=new Product();
		product.setName("added_prod_name");

		productRepository.add(product);

		em.flush();

		assertNotNull(product);
		assertTrue(product.getId()>0);

		log.info(""+product.getId());

		Product productInDB=productRepository.findByPK(product.getId());

		assertNotNull(productInDB);
		assertTrue(productInDB.getName().equals("added_prod_name"));
	}

	@Test
	public void testUdpate() {
		Product product=new Product();
		product.setName("product_for_update");

		productRepository.add(product);

		em.flush();

		Product productForUpdate=productRepository.findByPK(product.getId());

		assertTrue(productForUpdate.getName().equals("product_for_update"));

		productForUpdate.setName("updated_prod_name");

		productRepository.udpate(productForUpdate);

		em.flush();

		Product productUpdated=productRepository.findByPK(product.getId());
		assertNotNull(productUpdated);
		assertTrue(productUpdated.getName().equals("updated_prod_name"));
	}

	@Test
	public void testFindByPK() {
		Product product=new Product();
		
		String product_for_find_name = "product_for_find_name";
		
		product.setName(product_for_find_name);

		productRepository.add(product);

		em.flush();

		Product productFinded=productRepository.findByPK(product.getId());
		assertNotNull(productFinded);
		assertTrue(productFinded.getName().equals(product_for_find_name));
	}

	@Test
	public void testFindByParamsEmpty(){
		Map<String, Object> params=new HashMap<String, Object>();

		Collection<ProductListItem> products=productRepository.findByParams(params);

		log.info("products.size():"+products.size());

		for (ProductListItem product : products) {
			log.info(product.toString());
		}

		assertFalse(products.isEmpty());
		assertTrue(products.size() == DEFAULT_PRODUCTS_COUNT);
	}

	@Test
	public void testFindByParamsNotEmpty(){

		Category category=new Category();
		String product_category_4_name = "product_category_4_name";

		category.setName(product_category_4_name);

		categoryRepository.add(category);


		Product product=new Product();
		String product_4_name = "product_4_name";
		product.setName(product_4_name);

		product.setCategory(category);

		productRepository.add(product);

		em.flush();


		Map<String, Object> params=new HashMap<String, Object>();

		params.put("category_name", product_category_4_name);

		Collection<ProductListItem> products=productRepository.findByParams(params);

		log.info("products.size():"+products.size());

		for (ProductListItem item : products) {
			log.info(item.toString());
		}

		assertFalse(products.isEmpty());
		assertTrue(products.size() == 1);

		ProductListItem firstFoundedProd=products.iterator().next();

		assertTrue(firstFoundedProd.getName().equals(product_4_name));
		assertTrue(firstFoundedProd.getCategoryName().equals(product_category_4_name));
	}
}
