package com.pentalog.nguzun.dao;

import static org.junit.Assert.*;
import java.util.Collection;
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

    private static long roleId1;
    private static long roleId2;

    @BeforeClass
    public static void setUp() throws Exception {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        for (Role group : roleDAO.retrive()) {
            roleDAO.delete(group.getId());
        }
        Role role = new Role.Builder()
        		.name("Role 1")
				.description("Description role 1")
				.build();
        roleId1 = roleDAO.create(role);

        role = new Role.Builder()
			.name("Role 2")
			.description("Description role 2")
			.build();
        roleId2 = roleDAO.create(role);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        for (Role group : roleDAO.retrive()) {
            roleDAO.delete(group.getId());
        }
    }

    @Test
    public void test1RetriveById() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        Role actualRole = (Role) roleDAO.retrive(roleId1);
        assertEquals("Role 1", actualRole.getName());
        assertEquals("Description role 1", actualRole.getDescription());
        
        actualRole = (Role) roleDAO.retrive(roleId2);
        assertEquals("Role 2", actualRole.getName());
        assertEquals("Description role 2", actualRole.getDescription());
    }

    @Test
    public void test2RetriveList() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        Collection<Role> list = roleDAO.retrive();
        assertEquals(2, list.size());
    }

    @Test
    public void test3Create() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
    	Role role = new Role.Builder()
			.name("Role 3")
			.description("Description for role 3")
			.build();
        roleDAO.create(role);
        Collection<Role> list = roleDAO.retrive();
        assertEquals(3, list.size());
    }
    
    @Test
    public void test5Delete() throws ExceptionDAO {
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        Collection<Role> list = roleDAO.retrive();
        assertEquals(3, list.size());
        roleDAO.delete(roleId2);
        list = roleDAO.retrive();
        assertEquals(2, list.size());
    }
    
    @Test
    public void test4Update() throws ExceptionDAO {
        RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
        Role role = (Role) roleDAO.retrive(roleId1);
        role.setName("Role 4");
        role.setDescription("Descriere role 4");
        roleDAO.update(role);
        Role updatedUser = (Role) roleDAO.retrive(roleId1);
        assertEquals("Role 4", updatedUser.getName());
        assertEquals("Descriere role 4", updatedUser.getDescription());
    }
}
