/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentalog.nguzun.file.xml;

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
	
	

    
	@Override
    protected Element createXmlNode(User user, Document doc) {
    		Element userElement = doc.createElement("user");
			
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(String.valueOf(user.getId())));
			userElement.appendChild(id);
	 
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(user.getName()));
			userElement.appendChild(name);
	 
			Element login = doc.createElement("login");
			login.appendChild(doc.createTextNode(user.getLogin()));
			userElement.appendChild(login);

			Element password = doc.createElement("password");
			password.appendChild(doc.createTextNode(user.getPassword()));
			userElement.appendChild(password);			

			Element groupId = doc.createElement("groupId");
			groupId.appendChild(doc.createTextNode(String.valueOf(user.getGroup().getId())));
			userElement.appendChild(groupId);
			
			return userElement;
    }
	
    
	@Override
	public String getTagName() {
		return "user";
	}

}
