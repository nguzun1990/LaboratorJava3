package com.pentalog.nguzun.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.pentalog.nguzun.common.ConnectionDB;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.log4j.Logger;

/**
 *
 * @author Guzun
 */
public class GroupDAO implements BaseDAO<Group> {

    public static final String GET_GROUP_ERROR_MSG = "Error on get group with id ";
    public static final String GET_GROUP_LIST_ERROR_MSG = "Errore get list of groups";
    public static final String UPDATE_GROUP_ERROR_MSG = "Errore on update group with id ";
    public static final String CREATE_GROUP_ERROR_MSG = "Errore on create group";
    public static final String DELETE_GROUP_ERROR_MSG = "Errore on delete group";
    
    public static final String SELECT_SINGLE_QUERY = "SELECT * FROM groups WHERE id = ?";
    public static final String SELECT_QUERY = "SELECT * FROM groups";
    public static final String UPDATE_QUERY = "UPDATE groups SET name = ?, description = ?, id_role = ? WHERE id = ?";
    public static final String INSERT_QUERY = "INSERT INTO groups (name, description, id_role)  VALUES (?, ?, ?)";
    public static final String DELETE_QUERY = "DELETE FROM groups WHERE id = ?";
    
    private static final Logger log = Logger.getLogger(GroupDAO.class.getName());
    private static GroupDAO instance;
    private RoleDAO roleDAO;
    private static SessionFactory factory; 
    
    private GroupDAO() {
    	try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
    }

    public static GroupDAO getInstance() {
		if (instance == null) {
			instance = new GroupDAO();
		}
		return instance;
	}
    
    public Group retrive(int id) throws ExceptionDAO {
    	Group entity = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			entity = (Group) session.get(Group.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(GET_GROUP_ERROR_MSG + id);
			e.printStackTrace();
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return entity;
    }

    public Collection<Group> retrive() throws ExceptionDAO {
    	Session session = factory.openSession();
		Transaction tx = null;
		Collection<Group> groupList;
		try {
			tx = session.beginTransaction();
			groupList = session.createQuery("FROM Group").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(GET_GROUP_LIST_ERROR_MSG);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);			
		} finally {
			session.close();
		}

		return groupList;
    }

    public boolean delete(int id) throws ExceptionDAO {
    	Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Group entity = (Group) session.get(Group.class, id);
			session.delete(entity);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(DELETE_GROUP_ERROR_MSG + id);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}
		
		return result;
    }

    public boolean update(Group group) throws ExceptionDAO {
    	Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Group entity = (Group) session.get(Group.class, group.getId());
			entity.setName(group.getName());
			entity.setDescription(group.getDescription());
			entity.setRole(group.getRole());
			session.update(entity);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(UPDATE_GROUP_ERROR_MSG + group.getId());
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return result;
    }


    public int create(Group group) throws ExceptionDAO {
    	Session session = factory.openSession();
		Transaction tx = null;
		Integer groupID = null;
		try {
			tx = session.beginTransaction();
			groupID = (Integer) session.save(group);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error(CREATE_GROUP_ERROR_MSG + groupID);
			e.printStackTrace();
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}
		return groupID;

    }

}
