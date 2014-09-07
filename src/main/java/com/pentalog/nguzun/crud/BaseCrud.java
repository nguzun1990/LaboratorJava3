package com.pentalog.nguzun.crud;

import static java.lang.System.out;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.pentalog.nguzun.dao.BaseDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.file.csv.BaseCsvProcessor;
import com.pentalog.nguzun.vo.BaseValueObject;

public class BaseCrud<T extends BaseValueObject> {
	
	protected static final Logger log = Logger.getLogger(RoleCrud.class.getName());
	
	
	public void get(BaseDAO<T> dao, BaseCsvProcessor<T> csv,
			String[] args, String fileName) {
		try {
			if (args.length < 3) {
				out.println("You must provide the id");
				return;
			}
			int id = Integer.parseInt(args[2]);
			T entity = dao.retrive(id);
			csv.writeEntityToFile(entity, fileName);
		} catch (ExceptionDAO e) {
			log.error("get: an error dao was occured: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void list(BaseDAO<T> dao, BaseCsvProcessor<T> csv, String fileName) {
		try {			
			Collection<T> list = dao.retrive();
			csv.writeEntitiesToFile(list, fileName);
		} catch (ExceptionDAO e) {
			log.error("list: an error dao was occured: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void update(BaseDAO<T> dao, BaseCsvProcessor<T> csv, String fileName) {
		try {
			Collection<T> list = csv.readEntitiesFromFile(fileName);
			for (T entity : list) {
				dao.update(entity);
			}
		} catch (ExceptionDAO e) {
			log.error("update: an error dao was occured: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void insert(BaseDAO<T> dao, BaseCsvProcessor<T> csv, String fileName) {
		try {
			Collection<T> list = csv.readEntitiesFromFile(fileName);
			for (T entity : list) {
				dao.create(entity);
			}
		} catch (ExceptionDAO e) {
			log.error("insert: an error dao was occured: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void delete(BaseDAO<T> dao, String[] args) {
		try {
			if (args.length < 3) {
				out.println("You must provide the user id");
				return;
			}
			int id = Integer.parseInt(args[2]);
			dao.delete(id);
		} catch (ExceptionDAO e) {
			log.error("delete: an error dao was occured: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}
