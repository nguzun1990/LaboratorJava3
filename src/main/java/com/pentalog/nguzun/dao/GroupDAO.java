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
    
    
    public Connection connection = null;
    private static final Logger log = Logger.getLogger(GroupDAO.class.getName());
    private static GroupDAO instance;
    private RoleDAO roleDAO;
    private static SessionFactory factory; 
    
    private GroupDAO() {
        connection = ConnectionDB.getInstance();//TODO revie if needed
        roleDAO  = DaoFactory.buildObject(RoleDAO.class);//TODO revie if needed
        try{
            factory = new Configuration().configure().buildSessionFactory();
         }catch (Throwable ex) { 
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
    
    public Group retrive(long id) throws ExceptionDAO {
        Group group = null;
        Role role = null;
        try {            
            PreparedStatement selectGroup = null;
            String statement = SELECT_SINGLE_QUERY;
            selectGroup =  (PreparedStatement) this.connection.prepareStatement(statement);
            selectGroup.setLong(1, id);
            ResultSet resultSet = (ResultSet) selectGroup.executeQuery();
            if (resultSet.next()) {     
            	role = roleDAO.retrive(resultSet.getInt("id_role"));
            	group = new Group.Builder()
            		.id(resultSet.getInt("id"))
            		.name(resultSet.getString("name"))
					.description(resultSet.getString("description"))
					.role(role)
					.build();
            }

        } catch (SQLException e) {
            log.error(GET_GROUP_ERROR_MSG + id);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        return group;
    }

    public Collection<Group> retrive() throws ExceptionDAO {
        Collection<Group> groupList = new ArrayList<Group>();
        try {
        	Group group = null;
        	Role role = null;
            PreparedStatement selectGroup = null;
            String statement = SELECT_QUERY;
            selectGroup = (PreparedStatement) this.connection.prepareStatement(statement);
            ResultSet resultSet = (ResultSet) selectGroup.executeQuery();
            while (resultSet.next()) {
            	role = roleDAO.retrive(resultSet.getInt("id_role"));
            	group = new Group.Builder()
	        		.id(resultSet.getInt("id"))
	        		.name(resultSet.getString("name"))
					.description(resultSet.getString("description"))
					.role(role)
					.build();
            	groupList.add(group);
            }
        } catch (SQLException e) {
            log.error(GET_GROUP_LIST_ERROR_MSG);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        return groupList;
    }

    public boolean delete(long id) throws ExceptionDAO {
        try {
            PreparedStatement deleteGroup = null;
            String statement = DELETE_QUERY;
            deleteGroup = (PreparedStatement) this.connection.prepareStatement(statement);
            deleteGroup.setLong(1, id);
            int result = deleteGroup.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error(DELETE_GROUP_ERROR_MSG + id);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        return false;
    }

    //TODO gandestete la o metoda ca sa nu faci update daca nu e nevoie NotDone
    public boolean update(Group group) throws ExceptionDAO {
    	try {
            PreparedStatement updateGroup = null;
            String statement = UPDATE_QUERY;
            updateGroup = (PreparedStatement) this.connection.prepareStatement(statement);
            updateGroup.setString(1, group.getName());
            updateGroup.setString(2, group.getDescription());
            updateGroup.setInt(3, group.getRole().getId());
            updateGroup.setInt(4, group.getId());
            int result = updateGroup.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error(UPDATE_GROUP_ERROR_MSG +  group.getId());
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        return false;
    }


    public long create(Group group) throws ExceptionDAO {
    	Session session = factory.openSession();
        Transaction tx = null;
        Integer groupID = null;
        try{
           tx = session.beginTransaction();
           groupID = (Integer) session.save(group); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return groupID;
    	
//        long lastInsertID = 0;
//        try {
//            PreparedStatement insertGroup = null;
//            String statement = INSERT_QUERY;
//            insertGroup = (PreparedStatement) this.connection.prepareStatement(statement);
//            insertGroup.setString(1, group.getName());
//            insertGroup.setString(2, group.getDescription());
//            insertGroup.setInt(3, group.getRole().getId());
//            int result = insertGroup.executeUpdate();
//            if (result != 0) {
//                lastInsertID = insertGroup.getLastInsertID();
//            }
//        } catch (SQLException e) {
//            log.error(CREATE_GROUP_ERROR_MSG);
//            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
//        }
//
//        return lastInsertID;
    }

}
