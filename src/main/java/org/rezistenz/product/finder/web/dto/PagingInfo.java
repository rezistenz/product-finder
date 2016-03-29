package org.rezistenz.product.finder.web.dto;

public class PagingInfo{

	private int pageNum;
	private int pageSize;

	private long itemsCount;

	private String orderDir;
	private String orderCol;

	public int getPageIndex() {
		return pageNum > 0 ? pageNum-1 : 0;
	}
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getItemsCount() {
		return itemsCount;
	}
	
	public int getSize() {
		return (int) itemsCount;
	}

	public void setItemsCount(long itemsCount) {
		this.itemsCount = itemsCount;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	public String getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(String orderCol) {
		this.orderCol = orderCol;
	}

	public long getPagesCount() {
		if(pageSize <= 0){
			throw new IllegalArgumentException("pageSize must be greatter zero");
		}
		long countPages = (long) Math.ceil( itemsCount / (double) pageSize);
		return countPages;
	}
	@Override
	public String toString() {
		return "PagingInfo [pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", itemsCount=" + itemsCount + ", orderDir=" + orderDir
				+ ", orderCol=" + orderCol + "]";
	}
	
}
