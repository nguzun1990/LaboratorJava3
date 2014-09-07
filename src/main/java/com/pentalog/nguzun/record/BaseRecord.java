package com.pentalog.nguzun.record;

import org.apache.log4j.Logger;

public abstract class BaseRecord {
	
	private static final Logger log = Logger.getLogger(BaseRecord.class.getName());
	
	public static String getString(String value) {		
		return value.trim();		
	}

	public static int getInt(String value) {
		int result = 0;
		try {
			result = Integer.parseInt(value.trim());			
			
        } catch (NumberFormatException e) {
        	log.error("BaseRecord: the value is not a number " + value, e);
        }
		
		return result;
	}
}
