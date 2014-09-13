package com.pentalog.nguzun.dao;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.pentalog.nguzun.common.ConnectionDB;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.User;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Guzun
 */
public class UserDAO implements BaseDAO<User> {

    public static final String GET_USER_ERROR_MSG = "Error on get user with id ";
    public static final String GET_USER_LIST_ERROR_MSG = "Errore get list of users";
    public static final String UPDATE_USER_ERROR_MSG = "Errore on update user with id ";
    public static final String CREATE_USER_ERROR_MSG = "Errore on create user";
    public static final String DELETE_USER_ERROR_MSG = "Errore on delete user";
    
    public static final String SELECT_SINGLE_QUERY = "SELECT * FROM user WHERE id = ?";
    public static final String SELECT_QUERY = "SELECT * FROM user";
    public static final String UPDATE_QUERY = "UPDATE user SET name = ?, login = ?, password = ?, id_group = ? WHERE id = ?";
    public static final String INSERT_QUERY = "INSERT INTO user(name, login, password, id_group) VALUES (?, ?, ?, ?)";
    public static final String DELETE_QUERY = "DELETE FROM user WHERE id = ?";
    
    private static final Logger log = Logger.getLogger(UserDAO.class.getName());
    private static UserDAO instance;
    private GroupDAO groupDAO;
    private static SessionFactory factory; 

    private UserDAO() {
        try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
    }

    public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

    
    public User retrive(int id) throws ExceptionDAO {
    	User entity = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			entity = (User) session.get(User.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(GET_USER_ERROR_MSG + id);
			e.printStackTrace();
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return entity;
    }

    
    public Collection<User> retrive() throws ExceptionDAO {
    	Session session = factory.openSession();
		Transaction tx = null;
		Collection<User> userList;
		try {
			tx = session.beginTransaction();
			userList = session.createQuery("FROM User").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(GET_USER_LIST_ERROR_MSG);
			e.printStackTrace();
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);			
		} finally {
			session.close();
		}

		return userList;
    }

    
    public boolean delete(int id) throws ExceptionDAO {
    	Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User entity = (User) session.get(User.class, id);
			session.delete(entity);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(DELETE_USER_ERROR_MSG + id);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}
		
		return result;
    }

    
    public boolean update(User user) throws ExceptionDAO {
    	Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User entity = (User) session.get(User.class, user.getId());
			entity.setName(user.getName());
			entity.setLogin(user.getLogin());
			entity.setPassword(user.getPassword());
			entity.setGroup(user.getGroup());
			session.update(entity);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(UPDATE_USER_ERROR_MSG + user.getId());
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return result;
    }

    
    public int create(User user) throws ExceptionDAO {
    	Session session = factory.openSession();
		Transaction tx = null;
		Integer userID = null;
		try {
			tx = session.beginTransaction();
			userID = (Integer) session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error(CREATE_USER_ERROR_MSG + userID);
			e.printStackTrace();
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}
		return userID;
    }
}
