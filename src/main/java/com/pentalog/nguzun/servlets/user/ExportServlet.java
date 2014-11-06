package com.pentalog.nguzun.servlets.user;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.file.BaseFileProcessor;
import com.pentalog.nguzun.file.UserFileService;
import com.pentalog.nguzun.vo.User;

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
		response.setContentType("text/html");
		String responseHtmlBody = StringUtils.EMPTY;
		
		try {			
			String fileType = request.getParameter("filetype");
			BaseFileProcessor<User> fileProcessor = null;	
			UserDAO dao = DaoFactory.buildObject(UserDAO.class);
			Collection<User> list = dao.retrive();
			
			if (fileType != null) {
				String fileName = "users." + fileType;
				fileProcessor = UserFileService.getFileProcessor(fileName);
				filePath = getServletContext().getRealPath("") + File.separator + fileName;
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
			
		} catch (Exception e) {
			responseHtmlBody = "window.parent.alert('An internal error was occured!');";
			log.error("Export servlet doGet: "  + e.getMessage(), e);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
