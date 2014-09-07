/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentalog.nguzun.file.xml;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.pentalog.nguzun.dao.RoleDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.file.csv.BaseCsvProcessor;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;

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
    public String createStringForEntity(Group group) {
        StringBuilder strBuilder = new StringBuilder("");
        strBuilder.append("<group>")
			.append("\n\t<id>" + group.getId() + "</id>")
	        .append("\n\t<name>" + group.getName() + "</name>")
	        .append("\n\t<description>" + group.getDescription() + "</description>")
	        .append("\n\t<roleId>" + group.getRole().getId() + "</roleId>")
	        .append("\n</group>")
	        .append('\n');

        return strBuilder.toString();
    }
    
    @Override
	public String getHeader() {
    	StringBuilder strBuilder = new StringBuilder();
    	strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
				.append('\n')
    			.append("<groups>")
    			.append('\n');	        
	        
    	return strBuilder.toString();
	}

	@Override
	public String getFooter() {
		StringBuilder strBuilder = new StringBuilder("</groups>");
    	
    	return strBuilder.toString();
	}

	@Override
	public String getTagName() {
		return "group";
	}

}
