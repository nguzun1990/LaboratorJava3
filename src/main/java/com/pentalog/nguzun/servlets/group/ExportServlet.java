package com.pentalog.nguzun.servlets.group;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.pentalog.nguzun.dao.GroupDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.file.BaseFileProcessor;
import com.pentalog.nguzun.file.csv.GroupCsvProcessor;
import com.pentalog.nguzun.file.xml.GroupXmlProcessor;
import com.pentalog.nguzun.vo.Group;

/**
 * Servlet implementation class export
 */
public class ExportServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(ExportServlet.class);
	private static final int BUFSIZE = 4096;
    private String filePath;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportServlet() {
        super();
    }
    
    public void init() throws ServletException
    {
    }
    

	public void destroy() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	

			String fileType = request.getParameter("filetype");
			BaseFileProcessor<Group> fileProcessor = null;	
			GroupDAO dao = DaoFactory.buildObject(GroupDAO.class);
			Collection<Group> list = dao.retrive();
			
			if (fileType != null) {
				if (fileType.equals("csv")) {
					fileProcessor = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
					filePath = getServletContext().getRealPath("") + File.separator + "groups.csv";
				}
				if (fileType.equals("xml")) {
					fileProcessor = FileProcessorFactory.buildObject(GroupXmlProcessor.class);
					filePath = getServletContext().getRealPath("") + File.separator + "groups.xml";					
				}
				if (fileProcessor != null) {
					fileProcessor.writeEntitiesToFile(list, filePath);
				}
				
			}

			File file = new File(filePath);
	        int length   = 0;
	        ServletOutputStream outStream = response.getOutputStream();
	        ServletContext context  = getServletConfig().getServletContext();
	        String mimetype = context.getMimeType(filePath);
	        
	        // sets response content type
	        if (mimetype == null) {
	            mimetype = "application/octet-stream";
	        }
	        response.setContentType(mimetype);
	        response.setContentLength((int)file.length());
	        String fileName = (new File(filePath)).getName();
	        
	        // sets HTTP header
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	        
	        byte[] byteBuffer = new byte[BUFSIZE];
	        DataInputStream in = new DataInputStream(new FileInputStream(file));
	        
	        // reads the file's bytes and writes them to the response stream
	        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
	        {
	            outStream.write(byteBuffer,0,length);
	        }
	        
	        in.close();
	        outStream.close();			
			
		} catch (ExceptionDAO e) {
			log.error("Export servlet doGet: an error dao was occured: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error("Export servlet doGet: "  + e.getMessage(), e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
