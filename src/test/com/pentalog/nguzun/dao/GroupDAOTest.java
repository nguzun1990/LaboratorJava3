package com.pentalog.nguzun.dao;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.Group;

/**
 *
 * @author Nicolai
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupDAOTest {

    private static long groupId1;
    private static long groupId2;

    @BeforeClass
    public static void setUp() throws Exception {
        GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        for (Group group : groupDAO.retrive()) {
            groupDAO.delete(group.getId());
        }
        Group group = new Group.Builder()
			.name("Grupa 1")
			.description("Description group 1")
			.idRole(2)
			.build();
        groupId1 = groupDAO.create(group);

        group = new Group.Builder()
			.name("Grupa 2")
			.description("Description group 2")
			.idRole(3)
			.build();
        groupId2 = groupDAO.create(group);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        for (Group group : groupDAO.retrive()) {
            groupDAO.delete(group.getId());
        }
    }

	@Test
    public void test1RetriveById() throws ExceptionDAO {
		GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        Group actualGroup = (Group) groupDAO.retrive(groupId1);
        assertEquals("Grupa 1", actualGroup.getName());
        assertEquals("Description group 1", actualGroup.getDescription());
        assertEquals(2, actualGroup.getIdRole());
        
        actualGroup = (Group) groupDAO.retrive(groupId2);
        assertEquals("Grupa 2", actualGroup.getName());
        assertEquals("Description group 2", actualGroup.getDescription());
        assertEquals(3, actualGroup.getIdRole());
    }

    @Test
    public void test2RetriveList() throws ExceptionDAO {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        Collection<Group> list = groupDAO.retrive();
        assertEquals(2, list.size());
    }

    @Test
    public void test3Create() throws ExceptionDAO {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
    	Group group = new Group.Builder()
			.name("Grupa 3")
			.description("Descrierea pentru grupa 3")
			.idRole(2)
			.build();
        groupDAO.create(group);
        Collection<Group> list = groupDAO.retrive();
        assertEquals(3, list.size());
    }
    
    @Test
    public void test5Delete() throws ExceptionDAO {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        Collection<Group> list = groupDAO.retrive();
        assertEquals(3, list.size());
        groupDAO.delete(groupId2);
        list = groupDAO.retrive();
        assertEquals(2, list.size());
    }
    
    @Test
    public void test4Update() throws ExceptionDAO {
    	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
        Group group = (Group) groupDAO.retrive(groupId1);
        group.setName("Group 4");
        group.setDescription("Descriere group 4");
        group.setIdRole(5);
        groupDAO.update(group);
        Group updatedUser = (Group) groupDAO.retrive(groupId1);
        assertEquals("Group 4", updatedUser.getName());
        assertEquals("Descriere group 4", updatedUser.getDescription());
        assertEquals(5, updatedUser.getIdRole());
    }
}
