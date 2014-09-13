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
import com.pentalog.nguzun.vo.Role;

/**
 *
 * @author Nicolai
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleDAOTest {

	private static List<Integer> roleIds = new ArrayList<Integer>();

    @BeforeClass
    public static void setUp() throws Exception {
    	Integer id;
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        Role role = new Role.Builder()
        		.name("Role 1")
				.description("Description role 1")
				.build();
        id = roleDAO.create(role);
        roleIds.add(id);

        role = new Role.Builder()
			.name("Role 2")
			.description("Description role 2")
			.build();
        id = roleDAO.create(role);
        roleIds.add(id);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
    	for (int roleId : roleIds) {
    		roleDAO.delete(roleId);
    	}
    }

    @Test
    public void test1RetriveById() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        Role actualRole = roleDAO.retrive(roleIds.get(0));
        assertEquals("Role 1", actualRole.getName());
        assertEquals("Description role 1", actualRole.getDescription());
        
        actualRole = roleDAO.retrive(roleIds.get(1));
        assertEquals("Role 2", actualRole.getName());
        assertEquals("Description role 2", actualRole.getDescription());
    }

    @Test
    public void test2RetriveList() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        Collection<Role> list = roleDAO.retrive();
        assertTrue(list.size() > 0);
    }

    @Test
    public void test3Create() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
    	Role role = new Role.Builder()
			.name("Role 3")
			.description("Description for role 3")
			.build();
        int insertedRoleID = roleDAO.create(role);
        Role actualRole = roleDAO.retrive(insertedRoleID);
        assertEquals(role.getName(), actualRole.getName());
        assertEquals(role.getDescription(), actualRole.getDescription());
    }
    
    @Test
    public void test5Delete() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
    	Role role = roleDAO.retrive(roleIds.get(1));
    	assertNotNull(role);
    	roleDAO.delete(roleIds.get(1));
    	role = roleDAO.retrive(roleIds.get(1));
    	assertNull(role);
    	roleIds.remove(1);
    }
    
    @Test
    public void test4Update() throws ExceptionDAO {
        RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        Role role = (Role) roleDAO.retrive(roleIds.get(0));
        role.setName("Role 4");
        role.setDescription("Descriere role 4");
        roleDAO.update(role);
        Role updatedRole = (Role) roleDAO.retrive(roleIds.get(0));
        assertEquals("Role 4", updatedRole.getName());
        assertEquals("Descriere role 4", updatedRole.getDescription());
    }
}
