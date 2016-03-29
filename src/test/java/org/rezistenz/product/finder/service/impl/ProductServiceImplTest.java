package org.rezistenz.product.finder.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rezistenz.product.finder.model.Category;
import org.rezistenz.product.finder.model.Product;
import org.rezistenz.product.finder.persistence.CategoryRepository;
import org.rezistenz.product.finder.service.ProductService;
import org.rezistenz.product.finder.web.dto.PagingInfo;
import org.rezistenz.product.finder.web.dto.ProductFilter;
import org.rezistenz.product.finder.web.dto.ProductForm;
import org.rezistenz.product.finder.web.dto.ProductListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/META-INF/applicationContextService.xml"})
@Transactional
public class ProductServiceImplTest {

	private static final int PAGE_SIZE = 5;

	private static Logger log=Logger.getLogger(ProductServiceImplTest.class.getName());
	
	private static final String CATEGORY_NAME_COMPUTERS = "computers";

	private static final int DEFAULT_PRODUCTS_COUNT = 7;
	private static final int PRODUCTS_CATEGORY_COMPUTERS_COUNT = 3;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindProductsWithEmptyParams() {
		ProductFilter productFilter=new ProductFilter();
		
		PagingInfo pagingInfo=new PagingInfo();
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		assertTrue(products.size() == DEFAULT_PRODUCTS_COUNT);
	}

	@Test
	public void testFindProductsWithNotEmptyParams() {
		ProductFilter productFilter=new ProductFilter();
		productFilter.setCategoryName(CATEGORY_NAME_COMPUTERS);
		
		PagingInfo pagingInfo=new PagingInfo();
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		assertTrue(products.size() == PRODUCTS_CATEGORY_COMPUTERS_COUNT);
	}
	
	@Test
	public void testGetProductsCountForEmptyCategory() {
		ProductFilter productFilter=new ProductFilter();
		
		long productsCount = productService.getProductsCount(productFilter);
		
		assertTrue(productsCount == DEFAULT_PRODUCTS_COUNT);
	}

	@Test
	public void testGetProductsCountForNotEmptyCategory() {
		ProductFilter productFilter=new ProductFilter();
		productFilter.setCategoryName(CATEGORY_NAME_COMPUTERS);
		
		long productsCount = productService.getProductsCount(productFilter);
		
		assertTrue(productsCount == PRODUCTS_CATEGORY_COMPUTERS_COUNT);
	}
	
	@Test
	public void testFindProductsWithNameStart() {
		ProductFilter productFilter=new ProductFilter();
		productFilter.setProductName("a");
		
		PagingInfo pagingInfo=new PagingInfo();
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		assertTrue(products.size() == 2);
	}
	
	@Test
	public void testFindProductsWithPriceFrom() {
		ProductFilter productFilter=new ProductFilter();
		
		productFilter.setPriceFrom(BigDecimal.valueOf(30000.0D));
		
		PagingInfo pagingInfo=new PagingInfo();
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		assertTrue(products.size() == 3);
	}
	
	@Test
	public void testFindProductsWithPriceTo() {
		ProductFilter productFilter=new ProductFilter();
		
		productFilter.setPriceTo(BigDecimal.valueOf(20000.0D));
		
		PagingInfo pagingInfo=new PagingInfo();
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		assertTrue(products.size() == 2);
	}
	
	@Test
	public void testFindProductsWithPriceFromAndTo() {
		ProductFilter productFilter=new ProductFilter();
		
		productFilter.setPriceFrom(BigDecimal.valueOf(20000.0D));
		productFilter.setPriceTo(BigDecimal.valueOf(30000.0D));
		
		PagingInfo pagingInfo=new PagingInfo();
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		assertTrue(products.size() == 2);
	}
	
	@Test
	public void testFindProductsWithPagingFirstPage() {
		int pageNum = 1;
		
		ProductFilter productFilter=new ProductFilter();
		
		PagingInfo pagingInfo=new PagingInfo();
		pagingInfo.setPageSize(PAGE_SIZE);
		pagingInfo.setPageNum(pageNum);
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		log.info("products.size(): "+products.size());
		
		for (ProductListItem product : products) {
			log.info(product.toString());
		}
		
		assertTrue(products.size() == PAGE_SIZE);
	}
	
	@Test
	public void testFindProductsWithPagingSecondPage() {
		int countPages = (int) Math.ceil( DEFAULT_PRODUCTS_COUNT / (double)PAGE_SIZE);
		int lastPageIndex = countPages - 1;
		
		int pageNum = lastPageIndex+1;
		
		ProductFilter productFilter=new ProductFilter();
		
		PagingInfo pagingInfo=new PagingInfo();
		pagingInfo.setPageSize(PAGE_SIZE);
		pagingInfo.setPageNum(pageNum);
		
		Collection<ProductListItem> products=productService.findProducts(productFilter, pagingInfo);
		
		log.info("products.size(): "+products.size());
		
		for (ProductListItem product : products) {
			log.info(product.toString());
		}
		
		int countOnLastPage= DEFAULT_PRODUCTS_COUNT % PAGE_SIZE;
		
		log.info("countOnLastPage: "+countOnLastPage);
		
		assertTrue(products.size() == countOnLastPage);
	}
	
	@Test
	public void testSaveCreate() throws Exception {
		ProductForm productForm=new ProductForm();
		
		productForm.setName("created product");
		productForm.setPrice(new BigDecimal(12345.42));
		
		Category category=categoryRepository.findByPK(3);
		
		assertNotNull(category);
		
		productForm.setCategoryId(category.getId());
		
		assertTrue(productForm.getId() == 0);
		
		productService.save(productForm);
		
		em.flush();
		
		assertTrue(productForm.getId() > 0L);
		
		Product product=productService.findById(productForm.getId());
		
		assertNotNull(product);
		
		assertTrue(product.getName().equals(productForm.getName()));
		assertTrue(product.getPrice().equals(productForm.getPrice()));
		assertTrue(product.getCategory().getId().equals(productForm.getCategoryId()));
	}
	
	@Test
	public void testSaveUpdate() throws Exception {
		
		Product productForUpdate=productService.findById(7);
		
		assertNotNull(productForUpdate);
		
		ProductForm productForm=new ProductForm(productForUpdate);
		
		productForm.setName("updated product");
		productForm.setPrice(new BigDecimal(54321.24));
		
		Category category=categoryRepository.findByPK(5);
		
		assertNotNull(category);
		
		productForm.setCategoryId(category.getId());
		
		assertTrue(productForm.getId() == 7);
		
		productService.save(productForm);
		
		em.flush();
		
		assertTrue(productForm.getId() > 0);
		assertTrue(productForm.getId() == 7);
		
		Product product=productService.findById(productForm.getId());
		
		assertNotNull(product);
		
		assertTrue(product.getName().equals(productForm.getName()));
		assertTrue(product.getPrice().equals(productForm.getPrice()));
		assertTrue(product.getCategory().getId().equals(productForm.getCategoryId()));
	}
}
