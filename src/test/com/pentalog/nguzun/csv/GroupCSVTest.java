package com.pentalog.nguzun.csv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import com.pentalog.nguzun.file.csv.GroupCsvProcessor;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;

/**
 *
 * @author Guzun
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupCSVTest {

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
//            roleDAO.delete(group.getId());
        }
    }

 
    
	@Test
    public void test1CreateEntity() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		String[] record = {"3", "Admin", "Group for administrator", "3"};
		Group actualGroup = csv.createEntity(record);
		Group expectedGroup = new Group();
		expectedGroup.setId(3);
		expectedGroup.setName("Admin");;
		expectedGroup.setDescription("Group for administrator");
		Role role = new Role();
		role.setId(3);
		expectedGroup.setRole(role);
		Assert.assertEquals(expectedGroup, actualGroup);		
    }
	
	@Test
    public void test2CreateStringForEntity() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		Group group = new Group();
		group.setId(5);
		group.setName("User");
		group.setDescription("Group for users");
		Role role = new Role();
		role.setId(11);
		group.setRole(role);
		String actualString = csv.createStringForEntity(group, ",");
		Assert.assertEquals("5,User,Group for users,11\n", actualString);
    }
	
	@Test
	public void test3WriteEntityToFile() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		Group group = new Group();
		group.setId(5);
		group.setName("User");
		group.setDescription("Group for users");
		Role role = new Role();
		role.setId(11);
		group.setRole(role);
		csv.writeEntityToFile(group, "testGroupFile.csv");
		Collection<Group> expectedList = new ArrayList<Group>();
		expectedList.add(group);
		Collection<Group> actualList = csv.readEntitiesFromFile("testGroupFile.csv");
		Assert.assertEquals(expectedList.size(), expectedList.size());
		
		Iterator<Group> expectedItr = expectedList.iterator();
		Iterator<Group> actualItr = actualList.iterator();
		while(expectedItr.hasNext()) {
			Group expectedItem = expectedItr.next();
			Group actualItem = actualItr.next();
			Assert.assertEquals(expectedItem, actualItem);
		}

	}
	
	@Test
	public void test4WriteEntitiesToFile() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		Collection<Group> expectedList = new ArrayList<Group>();
		Group group = new Group();
		group.setId(7);
		group.setName("Administrator");
		group.setDescription("Group for administrators");
		Role role = new Role();
		role.setId(17);
		group.setRole(role);
		expectedList.add(group);		
		group = new Group();
		group.setId(8);
		group.setName("User");
		group.setDescription("Group for users");
		role = new Role();
		role.setId(99);
		group.setRole(role);
		expectedList.add(group);
		csv.writeEntitiesToFile(expectedList, "testGroupFile.csv");		
		
		Collection<Group> actualList = csv.readEntitiesFromFile("testGroupFile.csv");
		Assert.assertEquals(expectedList.size(), expectedList.size());
		
		Iterator<Group> expectedItr = expectedList.iterator();
		Iterator<Group> actualItr = actualList.iterator();
		while(expectedItr.hasNext()) {
			Group expectedItem = expectedItr.next();
			Group actualItem = actualItr.next();
			Assert.assertEquals(expectedItem, actualItem);
		}

	}

 }
