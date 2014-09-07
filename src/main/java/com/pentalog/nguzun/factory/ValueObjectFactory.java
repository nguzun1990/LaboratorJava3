package com.pentalog.nguzun.factory;

import org.apache.log4j.Logger;

import com.pentalog.nguzun.vo.BaseValueObject;


public class ValueObjectFactory  {
	
	private static final Logger log = Logger.getLogger(ValueObjectFactory.class.getName());

	public static <T extends BaseValueObject> T buildObject(Class<T> className) {
		try {
			T newInstance = className.newInstance();
			return newInstance;
		} catch (InstantiationException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		}
		return null;
	}

}
