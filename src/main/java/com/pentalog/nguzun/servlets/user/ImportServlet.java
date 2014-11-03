package com.pentalog.nguzun.servlets.user;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.file.BaseFileProcessor;
import com.pentalog.nguzun.file.UserImportService;
import com.pentalog.nguzun.file.csv.UserCsvProcessor;
import com.pentalog.nguzun.file.xml.UserXmlProcessor;
import com.pentalog.nguzun.vo.User;

/**
 * Servlet implementation class import
 */
public class ImportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(ImportServlet.class);
	private boolean isMultipart;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportServlet() {
		super();
	}

	public void init() throws ServletException {
	}

	public void destroy() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("c:\\temp"));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		try {	
			// Parse the request to get file items.
			List<FileItem> fileItems = upload.parseRequest(request);
			// Process the uploaded file items
			UserImportService.importFile(fileItems);
		} catch (Exception e) {
			log.error("Import servlet doPost: "  + e.getMessage(), e);
		}
		try {
			
			response.sendRedirect(request.getContextPath());
		} catch (Exception e) {
			log.error("Input-Output exception import servlet: "  + e.getMessage(), e);
		}
	}

}
