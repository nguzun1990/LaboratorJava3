package com.pentalog.nguzun.csv;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.pentalog.nguzun.dao.GroupDAO;
import com.pentalog.nguzun.dao.RoleDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.file.csv.UserCsvProcessor;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;
import com.pentalog.nguzun.vo.User;

/**
 *
 * @author Guzun
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserCSVTest {
	
	private static List<Integer> groupIds = new ArrayList<Integer>();
	private static List<Integer> roleIds = new ArrayList<Integer>();

	@BeforeClass
    public static void setUp() throws Exception {
		Integer id;
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
    	for (int groupId : groupIds) {
    		groupDAO.delete(groupId);
    	}
    	RoleDAO roleDAO = DaoFactory.buildObject(RoleDAO.class);
    	for (int roleId : roleIds) {
    		roleDAO.delete(roleId);
    	}
    }
	
	@Test
    public void test1CreateEntity() {
		UserCsvProcessor csv = FileProcessorFactory.buildObject(UserCsvProcessor.class);
		String[] record = {"3", "Nicu Guzun", "nicubalti", "pass_word", groupIds.get(0).toString()};
		User actualUser = csv.createEntity(record);
		
		assertEquals(3, actualUser.getId());
		assertEquals("Nicu Guzun", actualUser.getName());
		assertEquals("nicubalti", actualUser.getLogin());
		assertEquals("pass_word", actualUser.getPassword());
		assertEquals("Grupa 1", actualUser.getGroup().getName());
		assertEquals("Description group 1", actualUser.getGroup().getDescription());
    }
	
	@Test
    public void test2CreateStringForEntity() {
		UserCsvProcessor csv = FileProcessorFactory.buildObject(UserCsvProcessor.class);
		User user = new User();
		user.setId(5);
		user.setName("Nicu Guzun");;
		user.setLogin("nicubalti");
		user.setPassword("pass_word");
		
		Group group = new Group();
		group.setId(10);
		user.setGroup(group);
		String actualString = csv.createStringForEntity(user, ",");
		assertEquals("5,Nicu Guzun,nicubalti,pass_word,10\n", actualString);
    }
	
	@Test
	public void test3WriteEntityToFile() {
		UserCsvProcessor csv = FileProcessorFactory.buildObject(UserCsvProcessor.class);
		User user = new User();
		user.setId(5);
		user.setName("Nicu Guzun");;
		user.setLogin("nicubalti");
		user.setPassword("pass_word");
		
		Group group = new Group();
		group.setId(groupIds.get(0));
		user.setGroup(group);
		csv.writeEntityToFile(user, "testUserFile.csv");
		Collection<User> expectedList = new ArrayList<User>();
		expectedList.add(user);
		Collection<User> actualList = csv.readEntitiesFromFile("testUserFile.csv");
		assertEquals(expectedList.size(), expectedList.size());
		
		Iterator<User> expectedItr = expectedList.iterator();
		Iterator<User> actualItr = actualList.iterator();
		while(expectedItr.hasNext()) {
			User expectedItem = expectedItr.next();
			User actualItem = actualItr.next();
			assertEquals(expectedItem.getId(), actualItem.getId());
			assertEquals(expectedItem.getName(), actualItem.getName());
			assertEquals(expectedItem.getLogin(), actualItem.getLogin());
			assertEquals(expectedItem.getPassword(), actualItem.getPassword());
			assertEquals(expectedItem.getGroup().getId(), actualItem.getGroup().getId());
		}

	}
	
	@Test
	public void test4WriteEntitiesToFile() {
		UserCsvProcessor csv = FileProcessorFactory.buildObject(UserCsvProcessor.class);
		Collection<User> expectedList = new ArrayList<User>();
		User user = new User();
		user.setId(7);
		user.setName("Nicu Guzun");
		user.setLogin("nicubalti_username");
		user.setPassword("pass_word_1");
		Group group = new Group();
		group.setId(groupIds.get(0));
		user.setGroup(group);
		expectedList.add(user);		
		user = new User();
		user.setId(8);
		user.setName("Vasile Cazacu");
		user.setLogin("vcazacu_username");
		user.setPassword("pass_word_2");
		group = new Group();
		group.setId(groupIds.get(1));
		user.setGroup(group);
		expectedList.add(user);
		csv.writeEntitiesToFile(expectedList, "testUserFile.csv");		
		
		Collection<User> actualList = csv.readEntitiesFromFile("testUserFile.csv");
		assertEquals(expectedList.size(), expectedList.size());
		
		Iterator<User> expectedItr = expectedList.iterator();
		Iterator<User> actualItr = actualList.iterator();
		while(expectedItr.hasNext()) {
			User expectedItem = expectedItr.next();
			User actualItem = actualItr.next();
			assertEquals(expectedItem.getId(), actualItem.getId());
			assertEquals(expectedItem.getName(), actualItem.getName());
			assertEquals(expectedItem.getLogin(), actualItem.getLogin());
			assertEquals(expectedItem.getPassword(), actualItem.getPassword());
			assertEquals(expectedItem.getGroup().getId(), actualItem.getGroup().getId());
		}

	}

 }
