package com.sie.saaf.schedule.utils;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 当前页的起始点
	 */
	private int offset;

	/**
	 * 当前页
	 */
	private int curPage;
	
	/**
	 * 设定的页面数目
	 */
	private int pageSize;

	/**
	 * 总页数
	 */
	private int pageCount;

	/**
	 * 该查询总共可返回的数目
	 */
	private int total;	
	
	/**
	 * 参数
	 */
	private String param;

	/**
	 * 数据列表
	 */

	private List<T> data;

	
	/**
	 * @param curPage 当前页
	 * @param pageSize 每页条数
	 * @param total 数据总条数
	 * @param data 取得的当页的对象
	 */
	public PageBean(int curPage, int pageSize, int total, List<T> data) {
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.total = total;
		this.data = data;
		this.pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1 ;
	}

	public PageBean(int curPage, int pageSize, int total, String param, List<T> data) {
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.total = total;
		this.param = param;
		this.data = data;
		this.pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1 ;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
	
}
