package com.zlebank.zplatform.trade.common.page;

import java.io.Serializable;
import java.util.List;

public class PageVo<T> implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4733550294054309401L;
	//列表
	public List<T> list;
	//总条数
	public int total;
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
