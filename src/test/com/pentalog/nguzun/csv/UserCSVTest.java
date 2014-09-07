package com.pentalog.nguzun.csv;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.pentalog.nguzun.factory.FileProcessorFactory;
import com.pentalog.nguzun.file.csv.UserCsvProcessor;
import com.pentalog.nguzun.vo.User;

/**
 *
 * @author Guzun
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserCSVTest {

	@Test
    public void test1CreateEntity() {
		UserCsvProcessor csv = FileProcessorFactory.buildObject(UserCsvProcessor.class);
		String[] record = {"3", "Nicu Guzun", "nicubalti", "pass_word", "10"};
		User actualUser = csv.createEntity(record);
		User expectedUser = new User();
		expectedUser.setId(3);
		expectedUser.setName("Nicu Guzun");;
		expectedUser.setLogin("nicubalti");
		expectedUser.setPassword("pass_word");
		expectedUser.setIdGroup(10);
		assertEquals(expectedUser, actualUser);		
    }
	
	@Test
    public void test2CreateStringForEntity() {
		UserCsvProcessor csv = FileProcessorFactory.buildObject(UserCsvProcessor.class);
		User user = new User();
		user.setId(5);
		user.setName("Nicu Guzun");;
		user.setLogin("nicubalti");
		user.setPassword("pass_word");
		user.setIdGroup(10);
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
		user.setIdGroup(10);
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
			assertEquals(expectedItem, actualItem);
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
		user.setIdGroup(3);
		expectedList.add(user);		
		user = new User();
		user.setId(8);
		user.setName("Vasile Cazacu");
		user.setLogin("vcazacu_username");
		user.setPassword("pass_word_2");
		user.setIdGroup(5);
		expectedList.add(user);
		csv.writeEntitiesToFile(expectedList, "testUserFile.csv");		
		
		Collection<User> actualList = csv.readEntitiesFromFile("testUserFile.csv");
		assertEquals(expectedList.size(), expectedList.size());
		
		Iterator<User> expectedItr = expectedList.iterator();
		Iterator<User> actualItr = actualList.iterator();
		while(expectedItr.hasNext()) {
			User expectedItem = expectedItr.next();
			User actualItem = actualItr.next();
			assertEquals(expectedItem, actualItem);
		}

	}

 }
