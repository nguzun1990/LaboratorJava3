package com.pentalog.nguzun.dao;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.json.JSONObject;

import com.pentalog.nguzun.common.DependencyParams;
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

    
    public Collection<User> retrive(DependencyParams dependencyParams) throws ExceptionDAO {
    	Session session = factory.openSession();
		Collection<User> userList;
		try {
			Criteria criteria = session.createCriteria(User.class);
			if (dependencyParams != null) {
				criteria = sort(dependencyParams, criteria);
				criteria = filter(dependencyParams, criteria);
			}
			userList = criteria.list();
		} catch (HibernateException e) {
			log.error(GET_USER_LIST_ERROR_MSG);
			throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);	
		} catch (Exception e) {
			log.error(GET_USER_LIST_ERROR_MSG);
			throw new ExceptionDAO("Exception " + e.getMessage(), log);	
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


	public Collection<User> retrive() throws ExceptionDAO {
		Collection<User> list = retrive(null);
		
		return list;
	}
	
	public Criteria sort(DependencyParams dependencyParams, Criteria criteria) throws ExceptionDAO {
		String orders = dependencyParams.getOrders();
		Order order = null;
		
		try {
			if (orders != null) {
				JSONObject jsonObj = new JSONObject(orders);
				String orderBy = jsonObj.getString("orderBy");
				String direction = jsonObj.getString("direction");
				if (orderBy.equals("group")) {
					order = getOrder("name", direction);
					criteria.createCriteria("group")
	        				.addOrder(order);				
				} else {
					order = getOrder(orderBy, direction);
					criteria.addOrder(order);
				}
			}
		} catch (JSONException e) {
			log.error("Parse JSON Exception - UserDAO");
			throw new ExceptionDAO("JSON Exception " + e.getMessage(), log);
		} catch (Exception e) {
			log.error("Exception UserDAO - sort");
			throw new ExceptionDAO("Exception UserDAO" + e.getMessage(), log);
		}
		
		
		return criteria;
	}
	
	public Criteria filter(DependencyParams dependencyParams, Criteria criteria) throws ExceptionDAO {
		String filters = dependencyParams.getFilters();
		Criterion criterion = null;
		String name = null, login = null;
		Integer group = null;
		try {
			if (filters != null) {
				JSONObject jsonObj = new JSONObject(filters);	
				name = getFilterValueStr(jsonObj, "name");
				login = getFilterValueStr(jsonObj, "login");
				group = getFilterValueInt(jsonObj, "group");

				if (name != null) {
					criterion = Restrictions.eq("name", name);
					criteria.add(criterion);
				}
				if (login != null) {
					criterion = Restrictions.eq("login", login);
					criteria.add(criterion);
				}
				if (group != null) {
					criterion = Restrictions.eq("id", group);
					criteria.createCriteria("group")
							.add(criterion);
				}
			}
			
			
		} catch (JSONException e) {
			log.error("Parse JSON Exception - UserDAO");
			throw new ExceptionDAO("JSON Exception " + e.getMessage(), log);
		} catch (Exception e) {
			log.error("Exception UserDAO - filter");
			throw new ExceptionDAO("Exception UserDAO" + e.getMessage(), log);
		}
		
		return criteria;
	}
	
	public Order getOrder(String orderBy, String direction) {
		Order order = null;
		if (direction.equals("asc")) {
			order = Order.asc(orderBy);
		} else if (direction.equals("desc")) {
			order =Order.desc(orderBy);
		}
		
		return order;		
	}
	
	public String getFilterValueStr(JSONObject jsonObj, String property) throws JSONException {
		String value = null;
		if (jsonObj.has(property)) {
			value = jsonObj.getString(property);
		}
		
		return value;
	}
	
	public Integer getFilterValueInt(JSONObject jsonObj, String property) throws JSONException {
		Integer value = null;
		if (jsonObj.has(property)) {
			value = jsonObj.getInt(property);
		}
		
		return value;
	}
	
}
