package com.pentalog.nguzun.servlets.user;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pentalog.nguzun.file.UserFileService;

/**
 * Servlet implementation class import
 */
public class ImportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(ImportServlet.class);
	private boolean isMultipart;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		response.setContentType("text/html");
		String responseHtmlBody = StringUtils.EMPTY;
		
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
			UserFileService.importFile(fileItems);
		} catch (Exception e) {
			responseHtmlBody = "window.parent.alert('An internal error was occured!');";
			log.error("Import servlet doPost: "  + e.getMessage(), e);
		}
		
		if (StringUtils.isNotEmpty(responseHtmlBody)) {
			StringBuilder outStringBuilder = new StringBuilder();
			outStringBuilder.append(getResponseHtmlStart());
			outStringBuilder.append(responseHtmlBody);
			outStringBuilder.append(getResponseHtmlEnd());

			PrintWriter out = response.getWriter();
			out.println(outStringBuilder.toString());
			out.close();
		}
	}
	
    /**
     * Returns the starting section of a HTML document containing doctype, head,
     * and body/script start.
     * 
     * @return the start of the html
     */
    private String getResponseHtmlStart() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        html.append("<head>");
        html.append("<title>BPMS import file upload response</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("<script type=\"text/javascript\">");
        return html.toString();
    }
    
	/**
     * Returns the ending section of a HTML document.
     * 
     * @return the end of the html
     */
    private String getResponseHtmlEnd() {
        StringBuilder html = new StringBuilder();
        html.append("</script>");
        html.append("</body>");
        html.append("</html>");
        return html.toString();
    }
}
