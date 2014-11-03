package com.pentalog.nguzun.file;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.file.csv.UserCsvProcessor;
import com.pentalog.nguzun.file.xml.UserXmlProcessor;
import com.pentalog.nguzun.vo.User;

public class UserImportService {
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private static File file;
	
	public static void importFile(List<FileItem> fileItems) throws Exception {
			BaseFileProcessor<User> fileProcessor = null;		
			Iterator<?> iterator = fileItems.iterator();
			while (iterator.hasNext()) {
				FileItem fileItem = (FileItem) iterator.next();
				if (!fileItem.isFormField()) {
						file = new File(fileItem.getName()) ;
			            fileItem.write( file ) ;
						String ext = FilenameUtils.getExtension(file.getName());						
						if (ext.equals("csv")) {
							fileProcessor = FileProcessorFactory .buildObject(UserCsvProcessor.class);
						}
						if (ext.equals("xml")) {
							fileProcessor = FileProcessorFactory .buildObject(UserXmlProcessor.class);
						}
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
}
