/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentalog.nguzun.file.xml;

import java.io.File;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.pentalog.nguzun.dao.GroupDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.file.csv.BaseCsvProcessor;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.User;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 *
 * @author Guzun
 */
public class UserXmlProcessor extends BaseXmlProcessor<User> {
	
	protected static final Logger log = Logger.getLogger(BaseCsvProcessor.class.getName());
	
	private GroupDAO groupDAO;
	
	private static UserXmlProcessor instance;

    private UserXmlProcessor() {
    	groupDAO  = DaoFactory.buildObject(GroupDAO.class);
    }

    public static UserXmlProcessor getInstance() {
		if (instance == null) {
			instance = new UserXmlProcessor();
		}
		return instance;
	}

	@Override
	public User createEntity(Node node) {
		String name, login, password;
        int id, groupId;
        User user = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) {	 
			Element element =  (Element) node;
			id = getIntProperty("id", element);
	        groupId = getIntProperty("groupId", element);
	        name = getStringProperty("name", element);
	        login = getStringProperty("login", element);
	        password = getStringProperty("password", element);
	        Group group = null;
			try {
				group = groupDAO.retrive(groupId);
			} catch (ExceptionDAO e) {
				log.error("createEntity: an error dao was occured: " + e.getMessage(), e);
			}
			user = new User.Builder()
						.id(id)
						.name(name)
						.login(login)
						.password(password)
						.group(group)
						.build(); 
		}
		
		return user;
	}
	
	

//	@Override
//    public String createStringForEntity(User user) {
//        StringBuilder strBuilder = new StringBuilder("");
//        strBuilder.append("<user>")
//			.append("\n\t<id>" + user.getId() + "</id>")
//	        .append("\n\t<name>" + user.getName() + "</name>")
//	        .append("\n\t<login>" + user.getLogin() + "</login>")
//	        .append("\n\t<password>" + user.getPassword() + "</password>")
//	        .append("\n\t<groupId>" + user.getGroup().getId() + "</groupId>")
//	        .append("\n</user>")
//	        .append('\n');
//
//        return strBuilder.toString();
//    }
    
//    @Override
//	public String getHeader() {
//    	StringBuilder strBuilder = new StringBuilder();
//    	strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
//				.append('\n')
//    			.append("<users>")
//    			.append('\n');	        
//	        
//    	return strBuilder.toString();
//	}
    
    @Override
	public void writeEntitiesToFile(Collection<User> list, String pathFile) throws Exception {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("users");
		doc.appendChild(rootElement);
		
		for (User user : list) {
			Element staff = doc.createElement("user");
			rootElement.appendChild(staff);
			
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(String.valueOf(user.getId())));
			staff.appendChild(id);
	 
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(user.getName()));
			staff.appendChild(name);
	 
			Element login = doc.createElement("login");
			login.appendChild(doc.createTextNode(user.getLogin()));
			staff.appendChild(login);

			Element password = doc.createElement("password");
			password.appendChild(doc.createTextNode(user.getPassword()));
			staff.appendChild(password);			

			Element groupId = doc.createElement("groupId");
			groupId.appendChild(doc.createTextNode(String.valueOf(user.getGroup().getId())));
			staff.appendChild(groupId);
			
			
	    }
 
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(pathFile));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
		
//        try {
//            FileWriter writer = new FileWriter(pathFile);
//            String str = null;
//            writer.append(getHeader());
//            for (T entity : list) {
//                str = this.createStringForEntity(entity);
//                writer.append(str);
//            }
//            writer.append(getFooter());
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//        	log.error("writeEntitiesToFile: an error BaseXmlProcessor was occured: " + e.getMessage(), e);
//        }
    }

//	@Override
//	public String getFooter() {
//		StringBuilder strBuilder = new StringBuilder("</users>");
//    	
//    	return strBuilder.toString();
//	}
	
	@Override
	public String getTagName() {
		return "user";
	}

}
