package edu.buu.childhood.common;

/**
 * 分页类，提供分页相关计数
 * @author joe
 *
 */
public class Page {
	private int pageSize; // 每页记录的大小
	private int currentPage;// 当前第几页数据
	private int totalRecords;// 一共有多少条记录
	private int totalPage;// 一共有多少页

	public Page() {
		super();
	}

	public Page(int pageRecords, int pageNum, int pageSize) {
		this.totalRecords=pageRecords;
		this.currentPage=pageNum;
		this.pageSize=pageSize;
		totalPage=pageRecords/pageSize;
		if(pageRecords%pageSize==0){
			totalPage=pageRecords/pageSize;
		}else{
			totalPage=(pageRecords/pageSize)+1;
		}
	}

	public Page(int pageSize, int currentPage, int totalRecords, int totalPage) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalRecords = totalRecords;
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
