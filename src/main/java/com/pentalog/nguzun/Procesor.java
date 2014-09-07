package com.pentalog.nguzun;

import com.pentalog.nguzun.common.OperationEnum;
import com.pentalog.nguzun.crud.BaseCrud;
import com.pentalog.nguzun.crud.GroupCrud;
import com.pentalog.nguzun.crud.RoleCrud;
import com.pentalog.nguzun.crud.UserCrud;
import com.pentalog.nguzun.dao.BaseDAO;
import com.pentalog.nguzun.dao.GroupDAO;
import com.pentalog.nguzun.dao.RoleDAO;
import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.factory.CrudFactory;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.file.csv.BaseCsvProcessor;
import com.pentalog.nguzun.file.csv.GroupCsvProcessor;
import com.pentalog.nguzun.file.csv.RoleCsvProcessor;
import com.pentalog.nguzun.file.csv.UserCsvProcessor;
import com.pentalog.nguzun.vo.BaseValueObject;

public class Procesor {
	public static void processUser(OperationEnum operation, String[] args) {
		UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
		UserCsvProcessor userCSV = FileProcessorFactory.buildObject(UserCsvProcessor.class);
		UserCrud userCrud = CrudFactory.buildObject(UserCrud.class);
		String fileName = "usercsvfile.csv";
		process(operation, args, userDAO, userCSV, userCrud, fileName);
	}

	public static void processGroup(OperationEnum operation, String[] args) {
		GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
		GroupCsvProcessor groupCSV = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		GroupCrud groupCrud = CrudFactory.buildObject(GroupCrud.class);
		String fileName = "groupcsvfile.csv";
		process(operation, args, groupDAO, groupCSV, groupCrud, fileName);
	}

	public static void processRole(OperationEnum operation, String[] args) {
		RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
		RoleCsvProcessor roleCSV = FileProcessorFactory.buildObject(RoleCsvProcessor.class);
		RoleCrud roleCrud = CrudFactory.buildObject(RoleCrud.class);
		String fileName = "rolecsvfile.csv";
		process(operation, args, roleDAO, roleCSV, roleCrud, fileName);
	}

	
	private static <T extends BaseValueObject> void process(OperationEnum operation, String[] args,
			BaseDAO<T> dao, BaseCsvProcessor<T> csv, BaseCrud<T> crud, String fileName) {
		switch (operation) {
		case GET: {
			crud.get(dao, csv, args, fileName);
			break;
		}
		case LIST: {
			crud.list(dao, csv, fileName);
			break;
		}
		case INSERT: {
			crud.insert(dao, csv, fileName);
			break;
		}
		case UPDATE: {
			crud.update(dao, csv, fileName);
			break;
		}
		case DELETE: {
			crud.delete(dao, args);
			break;
		}
		}
	}

}
