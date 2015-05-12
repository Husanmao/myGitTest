package com.util;

import java.util.List;

public class PagerModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6201946643691574099L;
	private int offset = 0;//
	private int pagesize = 10;// 每页显示多少条记录
	private int total;// 总数
	private List<?> list;// 当前页结果集

	// Constructors
	public PagerModel() {
	}

	public PagerModel(int offset, int pagesize) {
		super();
		this.offset = offset;
		this.pagesize = pagesize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PagerModel [offset=" + offset + ", pagesize=" + pagesize
				+ ", total=" + total + "]";
	}

}