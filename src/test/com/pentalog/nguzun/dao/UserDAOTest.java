package com.pentalog.nguzun.dao;

import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.User;

import java.util.Collection;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Guzun
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOTest {

    private static long userId1;
    private static long userId2;

    @BeforeClass
    public static void setUp() throws Exception {
        UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        for (User user : userDAO.retrive()) {
            userDAO.delete(user.getId());
        }
        User user = new User.Builder()
        	.name("Nicolai Guzun")
    		.login("nicubalti")
    		.password("passnicu")
    		.idGroup(1)
    		.build();
        userId1 = userDAO.create(user);

        user = new User.Builder()
	    	.name("Vasile Cazacu")
			.login("vcazacu")
			.password("vasielepass")
			.idGroup(3)
			.build();
        userId2 = userDAO.create(user);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        for (User user : userDAO.retrive()) {
            userDAO.delete(user.getId());
        }
    }

	@Test
    public void test1RetriveById() throws ExceptionDAO {
		UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        User actualUser = (User) userDAO.retrive(userId1);
        assertEquals("Nicolai Guzun", actualUser.getName());
        assertEquals("nicubalti", actualUser.getLogin());
        assertEquals("passnicu", actualUser.getPassword());
        assertEquals(1, actualUser.getIdGroup());
        
        actualUser = (User) userDAO.retrive(userId2);
        assertEquals("Vasile Cazacu", actualUser.getName());
        assertEquals("vcazacu", actualUser.getLogin());
        assertEquals("vasielepass", actualUser.getPassword());
        assertEquals(3, actualUser.getIdGroup());
    }

    @Test
    public void test2RetriveList() throws ExceptionDAO  {
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        Collection<User> list = userDAO.retrive();
        assertEquals(2, list.size());
    }

    @Test
    public void test3Create() throws ExceptionDAO {
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
    	User user = new User.Builder()
	    	.name("Ciobanu Cristina")
			.login("cciobanu")
			.password("cristinapass")
			.idGroup(2)
			.build();
        userDAO.create(user);
        Collection<User> list = userDAO.retrive();
        assertEquals(3, list.size());
    }
    
    @Test
    public void test4Delete() throws ExceptionDAO {
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        Collection<User> list = userDAO.retrive();
        assertEquals(3, list.size());
        userDAO.delete(userId2);
        list = userDAO.retrive();
        assertEquals(2, list.size());
    }
    
    @Test
    public void test5Update() throws ExceptionDAO {
    	UserDAO userDAO = DaoFactory.buildObject(UserDAO.class);
        User user = (User) userDAO.retrive(userId1);
        user.setName("Nicu Guzun");
        user.setLogin("nguzun");
        user.setPassword("nguzunpass");
        user.setIdGroup(2);
        userDAO.update(user);
        User updatedUser = (User) userDAO.retrive(userId1);
        assertEquals("Nicu Guzun", updatedUser.getName());
        assertEquals("nguzun", updatedUser.getLogin());
        assertEquals("nguzunpass", updatedUser.getPassword());
        assertEquals(2, updatedUser.getIdGroup());
    }

    
}
