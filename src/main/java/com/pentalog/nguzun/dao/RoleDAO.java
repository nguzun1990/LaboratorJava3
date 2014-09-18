package com.pentalog.nguzun.dao;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.vo.Role;

/**
 * 
 * @author Guzun
 */
public class RoleDAO implements BaseDAO<Role> {

	public static final String GET_ROLE_ERROR_MSG = "Error on get role with id ";
	public static final String GET_ROLE_LIST_ERROR_MSG = "Errore get list of groups";
	public static final String UPDATE_ROLE_ERROR_MSG = "Errore on update role with id ";
	public static final String CREATE_ROLE_ERROR_MSG = "Errore on create role";
	public static final String DELETE_ROLE_ERROR_MSG = "Errore on delete role";


	private static final Logger log = Logger.getLogger(RoleDAO.class.getName());
	private static RoleDAO instance;
	private static SessionFactory factory;

	private RoleDAO() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static RoleDAO getInstance() {
		if (instance == null) {
			instance = new RoleDAO();
		}
		return instance;
	}

	public Role retrive(int id) throws ExceptionDAO {
		Role entity = null;
		Session session = factory.openSession();
		try {
			entity = (Role) session.get(Role.class, id);
		} catch (HibernateException e) {
			log.error(GET_ROLE_ERROR_MSG + id);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return entity;
	}

	public Collection<Role> retrive() throws ExceptionDAO {
		Session session = factory.openSession();
		Collection<Role> roleList;
		try {
			roleList = session.createQuery("FROM Role").list();
		} catch (HibernateException e) {
			log.error(GET_ROLE_LIST_ERROR_MSG);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);			
		} finally {
			session.close();
		}

		return roleList;
	}

	public boolean delete(int id) throws ExceptionDAO {
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Role entity = (Role) session.get(Role.class, id);
			session.delete(entity);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(DELETE_ROLE_ERROR_MSG + id);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}
		
		return result;
	}

	public boolean update(Role role) throws ExceptionDAO {
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Role entity = (Role) session.get(Role.class, role.getId());
			entity.setName(role.getName());
			entity.setDescription(role.getDescription());
			session.update(entity);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(UPDATE_ROLE_ERROR_MSG + role.getId());
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return result;
	}

	public int create(Role role) throws ExceptionDAO {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer roleID = null;
		try {
			tx = session.beginTransaction();
			roleID = (Integer) session.save(role);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error(CREATE_ROLE_ERROR_MSG + roleID);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}
		return roleID;
	}
}
