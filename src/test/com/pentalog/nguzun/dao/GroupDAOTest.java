package com.pentalog.nguzun.dao;

import static org.junit.Assert.assertEquals;

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

	private static List<Long> groupIds = new ArrayList<Long>();
	private static List<Long> roleIds = new ArrayList<Long>();
//	private static long[] groupIds;
//	private static long[] roleIds;

    @BeforeClass
    public static void setUp() throws Exception {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);    	
    	Role role = new Role.Builder()
			.name("role 1")
			.description("description 1")
			.build(); 
    	long id = roleDAO.create(role);
    	roleIds.add(id);

        Group group = new Group.Builder()
			.name("Grupa 1")
			.description("Description group 1")
			.role(role)
			.build();
        id = groupDAO.create(group);
        System.out.println("Idul role adaugat " + id);
        groupIds.add(id);

//        role = new Role.Builder()
//			.name("role 2")
//			.description("description 2")
//			.build(); 
//        roleIds.add(id);
//        
//        group = new Group.Builder()
//			.name("Grupa 2")
//			.description("Description group 2")
//			.role(role)
//			.build();
//        id = groupDAO.create(group);
//        groupIds.add(id);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        for (Group group : groupDAO.retrive()) {
//            groupDAO.delete(group.getId());
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
        assertEquals(2, list.size());
    }

//    @Test
//    public void test3Create() throws ExceptionDAO {
//    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
//    	Group group = new Group.Builder()
//			.name("Grupa 3")
//			.description("Descrierea pentru grupa 3")
//			.idRole(2)
//			.build();
//        groupDAO.create(group);
//        Collection<Group> list = groupDAO.retrive();
//        assertEquals(3, list.size());
//    }
//    
//    @Test
//    public void test5Delete() throws ExceptionDAO {
//    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
//        Collection<Group> list = groupDAO.retrive();
//        assertEquals(3, list.size());
//        groupDAO.delete(groupId2);
//        list = groupDAO.retrive();
//        assertEquals(2, list.size());
//    }
//    
//    @Test
//    public void test4Update() throws ExceptionDAO {
//    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
//        Group group = (Group) groupDAO.retrive(groupId1);
//        group.setName("Group 4");
//        group.setDescription("Descriere group 4");
//        group.setIdRole(5);
//        groupDAO.update(group);
//        Group updatedUser = (Group) groupDAO.retrive(groupId1);
//        assertEquals("Group 4", updatedUser.getName());
//        assertEquals("Descriere group 4", updatedUser.getDescription());
//        assertEquals(5, updatedUser.getIdRole());
//    }
}
