package com.pentalog.nguzun.file;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.file.csv.UserCsvProcessor;
import com.pentalog.nguzun.file.xml.UserXmlProcessor;
import com.pentalog.nguzun.vo.User;

public class UserFileService {
	private static File file;
	
	public static void importFile(List<FileItem> fileItems) throws Exception {
		BaseFileProcessor<User> fileProcessor = null;		
		Iterator<?> iterator = fileItems.iterator();
		while (iterator.hasNext()) {
			FileItem fileItem = (FileItem) iterator.next();
			if (!fileItem.isFormField()) {
					file = new File(fileItem.getName()) ;
		            fileItem.write( file ) ;
		            String fileName = file.getName();
		            fileProcessor = getFileProcessor(fileName);
					if (fileProcessor != null) {
						Collection<User> list = fileProcessor.readEntitiesFromFile(file.getName());
						UserDAO dao = DaoFactory.buildObject(UserDAO.class);
						for (User user : list) {
							if (user.getId() == 0) {
								dao.create(user);
							} else {
								dao.update(user);
							}
						}
					}	
			}
		}
	}
	
	public static BaseFileProcessor<User> getFileProcessor(String fileName) {
		Class classProcessor = null;
		String ext = FilenameUtils.getExtension(fileName);						
		if (ext.equals("csv")) {
			classProcessor = UserCsvProcessor.class;
		} else if (ext.equals("xml")) {
			classProcessor = UserXmlProcessor.class;			
		}
		BaseFileProcessor<User> fileProcessor = FileProcessorFactory.buildObject(classProcessor);
		
		return fileProcessor;
	}
}
