package com.pentalog.nguzun.dao;

import java.net.HttpRetryException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.vo.User;

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

    
    private static final Logger log = Logger.getLogger(UserDAO.class.getName());
    private static UserDAO instance;
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
		try {
			entity = (User) session.get(User.class, id);
		} catch (HibernateException e) {
			log.error(GET_USER_ERROR_MSG + id);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}

		return entity;
    }

    
    public Collection<User> retrive(HttpServletRequest request) throws ExceptionDAO {
    	Session session = factory.openSession();
		Collection<User> userList;
		
		//-----------------------------------------------23-09-2014
		Criteria criteria = session.createCriteria(User.class);
		criteria = sort(request, criteria);
		criteria = filter(request, criteria);
//		cr.addOrder(order);
		userList = criteria.list();
		
//		
//		Criteria cr = (Criteria) session
//				.createCriteria(User.class)
//                .createCriteria("group")
//                .addOrder(order);
		
//		userList = cr.list();
                
//		
		//-----------------------------------------------23-09-2014
		        
		
		try {
//			userList = session.createQuery("FROM User").list();
		} catch (HibernateException e) {
			log.error(GET_USER_LIST_ERROR_MSG);
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
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
		} finally {
			session.close();
		}
		return userID;
    }

	@Override
	public Collection<User> retrive() throws ExceptionDAO {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Criteria sort(HttpServletRequest request, Criteria criteria) {
		String sortBy = request.getParameter("sort[property]");
		String sortDirection = request.getParameter("sort[direction]");
		if (sortBy != null && sortDirection != null) {
			
		}
		return criteria;
	}
	
	public Criteria filter(HttpServletRequest request, Criteria criteria) {
		return criteria;
	}
}
