package org.rezistenz.product.finder.web.dto;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.rezistenz.product.finder.web.dto.PagingInfo;

public class PagingInfoTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetPagesCount_fail() {
		PagingInfo pagingInfo=new PagingInfo();
		
		pagingInfo.setItemsCount(0);
		pagingInfo.setPageSize(0);
		
		pagingInfo.getPagesCount();
	}
	
	@Test
	public void testGetPagesCount_zero() {
		PagingInfo pagingInfo=new PagingInfo();
		
		pagingInfo.setItemsCount(0);
		pagingInfo.setPageSize(10);
		
		assertTrue(pagingInfo.getPagesCount() == 0);
	}

	@Test
	public void testGetPagesCount_one() {
		PagingInfo pagingInfo=new PagingInfo();
		
		pagingInfo.setItemsCount(10);
		pagingInfo.setPageSize(10);
		
		assertTrue(pagingInfo.getPagesCount() == 1);
	}

	@Test
	public void testGetPagesCount_exactDivision() {
		PagingInfo pagingInfo=new PagingInfo();
		
		pagingInfo.setItemsCount(100);
		pagingInfo.setPageSize(10);
		
		assertTrue(pagingInfo.getPagesCount() == 10);
	}
	
	@Test
	public void testGetPagesCount_greet() {
		PagingInfo pagingInfo=new PagingInfo();
		
		pagingInfo.setItemsCount(17);
		pagingInfo.setPageSize(5);
		
		assertTrue(pagingInfo.getPagesCount() == 4);
	}
	
	@Test
	public void testGetPagesCount_less() {
		PagingInfo pagingInfo=new PagingInfo();
		
		pagingInfo.setItemsCount(13);
		pagingInfo.setPageSize(5);
		
		assertTrue(pagingInfo.getPagesCount() == 3);
	}
	
	@Test
	public void testGetPagesCount_equal() {
		PagingInfo pagingInfo=new PagingInfo();
		
		pagingInfo.setItemsCount(15);
		pagingInfo.setPageSize(5);
		
		assertTrue(pagingInfo.getPagesCount() == 3);
	}
}
