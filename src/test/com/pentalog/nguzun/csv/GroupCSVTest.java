package com.pentalog.nguzun.csv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.file.csv.GroupCsvProcessor;
import com.pentalog.nguzun.vo.Group;

/**
 *
 * @author Guzun
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupCSVTest {

	@Test
    public void test1CreateEntity() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		String[] record = {"3", "Admin", "Group for administrator", "10"};
		Group actualGroup = csv.createEntity(record);
		Group expectedGroup = new Group();
		expectedGroup.setId(3);
		expectedGroup.setName("Admin");;
		expectedGroup.setDescription("Group for administrator");
		expectedGroup.setIdRole(10);
		Assert.assertEquals(expectedGroup, actualGroup);		
    }
	
	@Test
    public void test2CreateStringForEntity() {
		GroupCsvProcessor csv = FileProcessorFactory.buildObject(GroupCsvProcessor.class);
		Group group = new Group();
		group.setId(5);
		group.setName("User");
		group.setDescription("Group for users");
		group.setIdRole(11);
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
		group.setIdRole(11);
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
		group.setIdRole(17);
		expectedList.add(group);		
		group = new Group();
		group.setId(8);
		group.setName("User");
		group.setDescription("Group for users");
		group.setIdRole(99);
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
