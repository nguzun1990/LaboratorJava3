package com.pentalog.nguzun.dao;

import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;
import com.pentalog.nguzun.vo.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Guzun
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOTest {

	private static List<Integer> userIds = new ArrayList<Integer>();
	private static List<Integer> groupIds = new ArrayList<Integer>();	
	private static List<Integer> roleIds = new ArrayList<Integer>();	

    @BeforeClass
    public static void setUp() throws Exception {
    	Integer id;
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);    	
    	Role role = new Role.Builder()
			.name("role 1")
			.description("description 1")
			.build(); 
    	id = roleDAO.create(role);
    	roleIds.add(id);
    	Group group = new Group.Builder()
				.name("Grupa 1")
				.description("Description group 1")
				.role(role)
				.build();    	
    	id = groupDAO.create(group);
    	groupIds.add(id);
    	
        User user = new User.Builder()
        	.name("Nicolai Guzun")
    		.login("nicubalti")
    		.password("passnicu")
    		.group(group)
    		.build();
        id = userDAO.create(user);
        userIds.add(id);
        
        group = new Group.Builder()
			.name("Grupa 2")
			.description("Description group 2")
			.role(role)
			.build();    	
		id = groupDAO.create(group);
		groupIds.add(id);
        
        user = new User.Builder()
	    	.name("Vasile Cazacu")
			.login("vcazacu")
			.password("vasielepass")
			.group(group)
			.build();
        id = userDAO.create(user);
        userIds.add(id);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class); 
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class); 
    	for (int userId : userIds) {
    		userDAO.delete(userId);
    	}
    	for (Integer groupId : groupIds) {
    		groupDAO.delete(groupId);
    	}
    	for (Integer roleId : roleIds) {
    		roleDAO.delete(roleId);
    	}
    }

	@Test
    public void test1RetriveById() throws ExceptionDAO {
		UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        User actualUser = (User) userDAO.retrive(userIds.get(0));
        assertEquals("Nicolai Guzun", actualUser.getName());
        assertEquals("nicubalti", actualUser.getLogin());
        assertEquals("passnicu", actualUser.getPassword());
        assertEquals("Grupa 1", actualUser.getGroup().getName());
        
        actualUser = (User) userDAO.retrive(userIds.get(1));
        assertEquals("Vasile Cazacu", actualUser.getName());
        assertEquals("vcazacu", actualUser.getLogin());
        assertEquals("vasielepass", actualUser.getPassword());
        assertEquals("Grupa 2", actualUser.getGroup().getName());
    }

    @Test
    public void test2RetriveList() throws ExceptionDAO  {
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        Collection<User> list = userDAO.retrive();
        assertTrue(list.size() > 0);
    }

    @Test
    public void test3Create() throws ExceptionDAO {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	Group group = groupDAO.retrive(groupIds.get(1));
    	
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
    	User user = new User.Builder()
	    	.name("Ciobanu Cristina")
			.login("cciobanu")
			.password("cristinapass")
			.group(group)
			.build();      
        int insertedUserId = userDAO.create(user);
        
        User actualUser = userDAO.retrive(insertedUserId);
        assertTrue(actualUser instanceof User);
        userIds.add(insertedUserId);
    }
    
    @Test
    public void test4Delete() throws ExceptionDAO {
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
    	User user = userDAO.retrive(userIds.get(1));
        assertTrue(user instanceof User);
        userDAO.delete(userIds.get(1));
        user = userDAO.retrive(userIds.get(1));
        assertNull(user);
        userIds.remove(1);
    }
    
    @Test
    public void test5Update() throws ExceptionDAO {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	Group group = groupDAO.retrive(groupIds.get(1));
    	    	
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        User user = (User) userDAO.retrive(userIds.get(0));
        user.setName("Nicu Guzun");
        user.setLogin("nguzun");
        user.setPassword("nguzunpass");
        user.setGroup(group);
        userDAO.update(user);
        User updatedUser = (User) userDAO.retrive(userIds.get(0));
        assertEquals("Nicu Guzun", updatedUser.getName());
        assertEquals("nguzun", updatedUser.getLogin());
        assertEquals("nguzunpass", updatedUser.getPassword());
        assertEquals("Grupa 2", updatedUser.getGroup().getName());
    }

    
}
