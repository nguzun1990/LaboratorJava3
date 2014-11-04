/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentalog.nguzun.file.xml;

import java.io.File;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.pentalog.nguzun.dao.RoleDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.file.csv.BaseCsvProcessor;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;
import com.pentalog.nguzun.vo.User;

/**
 *
 * @author Guzun
 */
public class GroupXmlProcessor extends BaseXmlProcessor<Group> {
	
	protected static final Logger log = Logger.getLogger(BaseCsvProcessor.class.getName());
	
	private static GroupXmlProcessor instance;

	private RoleDAO roleDAO;
	
    private GroupXmlProcessor() {
    	roleDAO  = DaoFactory.buildObject(RoleDAO.class);
    }

    public static GroupXmlProcessor getInstance() {
		if (instance == null) {
			instance = new GroupXmlProcessor();
		}
		return instance;
	}

	@Override
	public Group createEntity(Node node) {
		String name, description;
        int id, roleId;
        Group group = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) {	 
			Element element =  (Element) node;
			id = getIntProperty("id", element);
			roleId = getIntProperty("roleId", element);
	        name = getStringProperty("name", element);
	        description = getStringProperty("description", element);
	        Role role = null;
			try {
				role = roleDAO.retrive(roleId);
			} catch (ExceptionDAO e) {
				log.error("GroupCsvProcessor createEntity: an error dao was occured: " + e.getMessage(), e);
			}
			group = new Group.Builder()
						.id(id)
						.name(name)
						.description(description)
						.role(role)
						.build(); 
		}
		
		return group;
	}
	
	
	@Override
    protected Element createXmlNode(Group group, Document doc) {
    		Element userElement = doc.createElement("group");
			
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(String.valueOf(group.getId())));
			userElement.appendChild(id);
	 
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(group.getName()));
			userElement.appendChild(name);
	 
			Element login = doc.createElement("description");
			login.appendChild(doc.createTextNode(group.getDescription()));
			userElement.appendChild(login);

			Element groupId = doc.createElement("roleId");
			groupId.appendChild(doc.createTextNode(String.valueOf(group.getRole().getId())));
			userElement.appendChild(groupId);
			
			return userElement;
    }


	@Override
	public String getTagName() {
		return "group";
	}

}
