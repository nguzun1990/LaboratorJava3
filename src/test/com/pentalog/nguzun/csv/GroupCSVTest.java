package com.pentalog.nguzun.csv;

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
import com.pentalog.nguzun.file.csv.GroupCsvProcessor;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;

/**
 *
 * @author Guzun
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupCSVTest {

    private static List<Integer> roleIds = new ArrayList<Integer>();

    @BeforeClass
    public static void setUp() throws Exception {
    	int id;
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
    public void test1CreateEntity() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		String[] record = {"3", "Admin", "Group for administrator", roleIds.get(0).toString()};
		Group actualGroup = csv.createEntity(record);
		Assert.assertEquals(3, actualGroup.getId());
		Assert.assertEquals("Admin", actualGroup.getName());
		Assert.assertEquals("Group for administrator", actualGroup.getDescription());
		Assert.assertEquals("Role 1", actualGroup.getRole().getName());
		Assert.assertEquals("Description role 1", actualGroup.getRole().getDescription());

    }
	
	@Test
    public void test2CreateStringForEntity() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		Group group = new Group();
		group.setId(5);
		group.setName("Group");
		group.setDescription("Group for users");
		Role role = new Role();
		role.setId(11);
		group.setRole(role);
		String actualString = csv.createStringForEntity(group, ",");
		Assert.assertEquals("5,Group,Group for users,11\n", actualString);
    }
	
	@Test
	public void test3WriteEntityToFile() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		Group group = new Group();
		group.setId(5);
		group.setName("User");
		group.setDescription("Group for users");
		Role role = new Role();
		role.setId(roleIds.get(0));
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
			Assert.assertEquals(expectedItem.getId(), actualItem.getId());
			Assert.assertEquals(expectedItem.getName(), actualItem.getName());
			Assert.assertEquals(expectedItem.getRole().getId(), actualItem.getRole().getId());
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
		role.setId(roleIds.get(0));
		group.setRole(role);
		expectedList.add(group);		
		group = new Group();
		group.setId(8);
		group.setName("User");
		group.setDescription("Group for users");
		role = new Role();
		role.setId(roleIds.get(1));
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
			Assert.assertEquals(expectedItem.getId(), actualItem.getId());
			Assert.assertEquals(expectedItem.getName(), actualItem.getName());
			Assert.assertEquals(expectedItem.getDescription(), actualItem.getDescription());
			Assert.assertEquals(expectedItem.getRole().getId(), actualItem.getRole().getId());
		}

	}

 }
