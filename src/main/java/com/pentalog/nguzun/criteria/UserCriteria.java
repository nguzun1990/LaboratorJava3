package com.pentalog.nguzun.criteria;

import org.hibernate.criterion.Order;

public class UserCriteria {
	
	public static Order getOrder(String property, String direction) {
		
		Order order = null;
		
		if (direction.equals("asc")) {
			order = Order.asc(property);
		} else {
			order = Order.desc(property);
		}
		
		return order;
		
	}

}
