package com.pentalog.nguzun.dao;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.vo.Group;

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
   
    private static final Logger log = Logger.getLogger(GroupDAO.class.getName());
    private static GroupDAO instance;
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
		try {
			entity = (Group) session.get(Group.class, id);
		} catch (HibernateException e) {
			log.error(GET_GROUP_ERROR_MSG + id);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return entity;
    }

    public Collection<Group> retrive() throws ExceptionDAO {
    	Session session = factory.openSession();
		Collection<Group> groupList;
		try {
			groupList = session.createQuery("FROM Group").list();
		} catch (HibernateException e) {
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
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}
		return groupID;

    }

}
