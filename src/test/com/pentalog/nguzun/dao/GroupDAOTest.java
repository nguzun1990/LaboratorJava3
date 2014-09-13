package com.pentalog.nguzun.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;

/**
 *
 * @author Nicolai
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupDAOTest {

	private static List<Integer> groupIds = new ArrayList<Integer>();
	private static List<Integer> roleIds = new ArrayList<Integer>();


    @BeforeClass
    public static void setUp() throws Exception {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);    	
    	Role role = new Role.Builder()
			.name("role 1")
			.description("description 1")
			.build(); 
    	Integer id = roleDAO.create(role);
    	roleIds.add(id);

        Group group = new Group.Builder()
			.name("Grupa 1")
			.description("Description group 1")
			.role(role)
			.build();
        id = groupDAO.create(group);
        groupIds.add(id);

        role = new Role.Builder()
			.name("role 2")
			.description("description 2")
			.build(); 
        id = roleDAO.create(role);
        roleIds.add(id);
        
        group = new Group.Builder()
			.name("Grupa 2")
			.description("Description group 2")
			.role(role)
			.build();
        id = groupDAO.create(group);
        groupIds.add(id);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	for (Integer groupId : groupIds) {
    		groupDAO.delete(groupId);
    	}
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
    	for (int roleId : roleIds) {
    		roleDAO.delete(roleId);
    	}
    }

	@Test
    public void test1RetriveById() throws ExceptionDAO {
		GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        Group actualGroup = groupDAO.retrive(groupIds.get(0));
        assertEquals("Grupa 1", actualGroup.getName());
        assertEquals("Description group 1", actualGroup.getDescription());
        assertEquals("role 1", actualGroup.getRole().getName());
        
        actualGroup =  groupDAO.retrive(groupIds.get(1));
        assertEquals("Grupa 2", actualGroup.getName());
        assertEquals("Description group 2", actualGroup.getDescription());        
        assertEquals("role 2", actualGroup.getRole().getName());
    }

    @Test
    public void test2RetriveList() throws ExceptionDAO {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        Collection<Group> list = groupDAO.retrive();
        assertTrue(list.size() > 0);
    }

    @Test
    public void test3Create() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
    	Role role = roleDAO.retrive(roleIds.get(1));
    	
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	Group group = new Group.Builder()
			.name("Grupa 3")
			.description("Descrierea pentru grupa 3")
			.role(role)
			.build();
    	
    	int insertedGroupId = groupDAO.create(group);
        Group actualGroup = groupDAO.retrive(insertedGroupId);
        assertTrue(actualGroup instanceof Group);
        groupIds.add(insertedGroupId);
    }
    
    @Test
    public void test5Delete() throws ExceptionDAO {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	Group group = groupDAO.retrive(groupIds.get(1));
        assertTrue(group instanceof Group);
        groupDAO.delete(groupIds.get(1));
        group = groupDAO.retrive(groupIds.get(1));
        assertNull(group);
        groupIds.remove(1);
    }
    
    @Test
    public void test4Update() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
    	Role role = roleDAO.retrive(roleIds.get(1));
    	
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        Group group = (Group) groupDAO.retrive(groupIds.get(0));
        group.setName("Group 4");
        group.setDescription("Descriere group 4");
        group.setRole(role);
        groupDAO.update(group);
        Group updatedGroup = groupDAO.retrive(groupIds.get(0));
        assertEquals("Group 4", updatedGroup.getName());
        assertEquals("Descriere group 4", updatedGroup.getDescription());
        assertEquals("role 2", updatedGroup.getRole().getName());
    }
}
