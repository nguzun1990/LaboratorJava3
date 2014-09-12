package com.pentalog.nguzun.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.pentalog.nguzun.common.ConnectionDB;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
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
public class RoleDAO implements BaseDAO<Role> {

	public static final String GET_ROLE_ERROR_MSG = "Error on get role with id ";
	public static final String GET_ROLE_LIST_ERROR_MSG = "Errore get list of groups";
	public static final String UPDATE_ROLE_ERROR_MSG = "Errore on update role with id ";
	public static final String CREATE_ROLE_ERROR_MSG = "Errore on create role";
	public static final String DELETE_ROLE_ERROR_MSG = "Errore on delete role";

	public static final String SELECT_SINGLE_QUERY = "SELECT * FROM role WHERE id = ?";
	public static final String SELECT_QUERY = "SELECT * FROM role";
	public static final String UPDATE_QUERY = "UPDATE role SET name = ?, description = ? WHERE id = ?";
	public static final String INSERT_QUERY = "INSERT INTO role (name, description) VALUES (?, ?)";
	public static final String DELETE_QUERY = "DELETE FROM role WHERE id = ?";

	private static final Logger log = Logger.getLogger(RoleDAO.class.getName());
	public Connection connection = null;
	private static RoleDAO instance;
	private static SessionFactory factory;

	private RoleDAO() {
		connection = ConnectionDB.getInstance();
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

	public Role retrive(long id) throws ExceptionDAO {
		Boolean result = false;
		Role entity = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			entity = (Role) session.get(Role.class, id);
			tx.commit();
			result = true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(GET_ROLE_ERROR_MSG + id);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return entity;

		// Role role = null;
		// try {
		// PreparedStatement selectRole = null;
		// String statement = SELECT_SINGLE_QUERY;
		// selectRole = (PreparedStatement) this.connection
		// .prepareStatement(statement);
		// selectRole.setLong(1, id);
		// ResultSet resultSet = (ResultSet) selectRole.executeQuery();
		// if (resultSet.next()) {
		// role = new Role.Builder().id(resultSet.getInt("id"))
		// .name(resultSet.getString("name"))
		// .description(resultSet.getString("description"))
		// .build();
		// }
		//
		// } catch (SQLException e) {
		// log.error(GET_ROLE_ERROR_MSG + id);
		// throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		// }
		// return role;
	}

	public Collection<Role> retrive() throws ExceptionDAO {
		Session session = factory.openSession();
		Transaction tx = null;
		Collection<Role> roleList;
		try {
			tx = session.beginTransaction();
			roleList = session.createQuery("FROM role").list();
			// for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
			// Role role = (Role) iterator.next();
			// System.out.print("First Name: " + employee.getFirstName());
			// System.out.print("  Last Name: " + employee.getLastName());
			// System.out.println("  Salary: " + employee.getSalary());
			// }
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error(GET_ROLE_LIST_ERROR_MSG);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		// Collection<Role> roleList = new ArrayList<Role>();
		// try {
		// Role role = null;
		// PreparedStatement selectRole = null;
		// String statement = SELECT_QUERY;
		// selectRole = (PreparedStatement) this.connection
		// .prepareStatement(statement);
		// ResultSet resultSet = (ResultSet) selectRole.executeQuery();
		// while (resultSet.next()) {
		// role = new Role.Builder().id(resultSet.getInt("id"))
		// .name(resultSet.getString("name"))
		// .description(resultSet.getString("description"))
		// .build();
		// roleList.add(role);
		// }
		// } catch (SQLException e) {
		// log.error(GET_ROLE_LIST_ERROR_MSG);
		// throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		// }
		return roleList;
	}

	public boolean delete(long id) throws ExceptionDAO {
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

//		try {
//			PreparedStatement deleteRole = null;
//			String statement = DELETE_QUERY;
//			deleteRole = (PreparedStatement) this.connection
//					.prepareStatement(statement);
//			deleteRole.setLong(1, id);
//			int result = deleteRole.executeUpdate();
//			if (result != 0) {
//				return true;
//			}
//		} catch (SQLException e) {
//			log.error(DELETE_ROLE_ERROR_MSG + id);
//			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
//		}
//		return false;
	}

	public boolean update(Role role) throws ExceptionDAO {
		Boolean result = false;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Role entity = (Role) session.get(Role.class, role.getId());
			entity.setName(entity.getName());
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

	public long create(Role role) throws ExceptionDAO {
		Session session = factory.openSession();
		Transaction tx = null;
		Long roleID = null;
		try {
			tx = session.beginTransaction();
			roleID = (Long) session.save(role);
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
