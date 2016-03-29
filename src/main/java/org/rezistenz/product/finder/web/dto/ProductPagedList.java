package org.rezistenz.product.finder.web.dto;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class ProductPagedList implements PaginatedList {

	private PagingInfo pagingInfo;
	private Collection<ProductListItem> productListItem;
	
	public ProductPagedList(PagingInfo pagingInfo,
			Collection<ProductListItem> productListItem) {
		this.pagingInfo=pagingInfo;
		this.productListItem=productListItem;
	}

	@Override
	public int getFullListSize() {
		return pagingInfo.getSize();
	}

	@Override
	public List getList() {
		return new LinkedList<ProductListItem>(productListItem);
	}

	@Override
	public int getObjectsPerPage() {
		return pagingInfo.getPageSize();
	}

	@Override
	public int getPageNumber() {
		return pagingInfo.getPageIndex()+1;
	}

	@Override
	public String getSearchId() {
		return "productPagedList";
	}

	@Override
	public String getSortCriterion() {
		return pagingInfo.getOrderCol();
	}

	@Override
	public SortOrderEnum getSortDirection() {
		return (pagingInfo.getOrderDir()!=null && pagingInfo.getOrderDir().equals("asc")) ? 
				SortOrderEnum.ASCENDING : SortOrderEnum.DESCENDING;
	}

}
