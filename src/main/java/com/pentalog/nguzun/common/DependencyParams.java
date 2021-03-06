package com.pentalog.nguzun.common;

import javax.servlet.http.HttpServletRequest;

public class DependencyParams {
	
	public DependencyParams(HttpServletRequest request) {
		setOrders(request.getParameter("orders"));
		setFilters(request.getParameter("filters"));		
	}
	
	private String orderBy;
	
	private String orders;
	
	private String direction;
	
	private String filters;
	
	private String filterBy;
	
	private String filterValue;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}
	
	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}	
	
	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}
	
	
}
