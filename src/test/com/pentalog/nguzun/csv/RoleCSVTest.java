package com.pentalog.nguzun.csv;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.file.csv.RoleCsvProcessor;
import com.pentalog.nguzun.vo.Role;

/**
 *
 * @author Guzun
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleCSVTest {

	@Test
    public void test1CreateEntity() {
		RoleCsvProcessor csv = FileProcessorFactory.buildObject(RoleCsvProcessor.class);
		String[] record = {"3", "Role1", "Description for role 1"};
		Role actualRole = csv.createEntity(record);
		Role expectedRole = new Role();
		expectedRole.setId(3);
		expectedRole.setName("Role1");;
		expectedRole.setDescription("Description for role 1");
		assertEquals(expectedRole, actualRole);		
    }
	
	@Test
    public void test2CreateStringForEntity() {
		RoleCsvProcessor csv = FileProcessorFactory.buildObject(RoleCsvProcessor.class);
		Role role = new Role();
		role.setId(5);
		role.setName("Role 1");
		role.setDescription("Description for role 1");
		String actualString = csv.createStringForEntity(role, ",");
		assertEquals("5,Role 1,Description for role 1\n", actualString);
    }
	
	@Test
	public void test3WriteEntityToFile() {
		RoleCsvProcessor csv = FileProcessorFactory.buildObject(RoleCsvProcessor.class);
		Role role = new Role();
		role.setId(5);
		role.setName("User");
		role.setDescription("Group for users");
		csv.writeEntityToFile(role, "testRoleFile.csv");
		Collection<Role> expectedList = new ArrayList<Role>();
		expectedList.add(role);
		Collection<Role> actualList = csv.readEntitiesFromFile("testRoleFile.csv");
		assertEquals(expectedList.size(), expectedList.size());
		
		Iterator<Role> expectedItr = expectedList.iterator();
		Iterator<Role> actualItr = actualList.iterator();
		while(expectedItr.hasNext()) {
			Role expectedItem = expectedItr.next();
			Role actualItem = actualItr.next();
			assertEquals(expectedItem, actualItem);
		}

	}
	
	@Test
	public void test4WriteEntitiesToFile() {
		RoleCsvProcessor csv = FileProcessorFactory.buildObject(RoleCsvProcessor.class);
		Collection<Role> expectedList = new ArrayList<Role>();
		Role role = new Role();
		role.setId(7);
		role.setName("Administrator");
		role.setDescription("Group for administrators");
		expectedList.add(role);		
		role = new Role();
		role.setId(8);
		role.setName("User");
		role.setDescription("Group for users");
		expectedList.add(role);
		csv.writeEntitiesToFile(expectedList, "testRoleFile.csv");		
		
		Collection<Role> actualList = csv.readEntitiesFromFile("testRoleFile.csv");
		assertEquals(expectedList.size(), expectedList.size());
		
		Iterator<Role> expectedItr = expectedList.iterator();
		Iterator<Role> actualItr = actualList.iterator();
		while(expectedItr.hasNext()) {
			Role expectedItem = expectedItr.next();
			Role actualItem = actualItr.next();
			assertEquals(expectedItem, actualItem);
		}

	}

 }
