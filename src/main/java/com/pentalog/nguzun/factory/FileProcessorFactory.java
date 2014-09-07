package com.pentalog.nguzun.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.pentalog.nguzun.file.BaseFileProcessor;
import com.pentalog.nguzun.vo.BaseValueObject;


public class FileProcessorFactory {
	
	private static final Logger log = Logger.getLogger(FileProcessorFactory.class.getName());
	
	public static  <T extends BaseFileProcessor<? extends BaseValueObject>> T buildObject(Class<T> classCSV) {
		try {

			Method getInstanceMethod = classCSV.getMethod("getInstance");
			@SuppressWarnings("unchecked")
			T newInstance = (T) getInstanceMethod.invoke(null);
			return newInstance;
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
