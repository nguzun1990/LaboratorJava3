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
    
    public Connection connection = null;
    private static final Logger log = Logger.getLogger(UserDAO.class.getName());
    private static UserDAO instance;
    private GroupDAO groupDAO;

    private UserDAO() {
        connection = ConnectionDB.getInstance();
        groupDAO  = DaoFactory.buildObject(GroupDAO.class);
    }

    public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

    
    public User retrive(long id) throws ExceptionDAO {
        User user = null;
        Group group = null;
        try {
            PreparedStatement selectUser = null;
            String statement = SELECT_SINGLE_QUERY;
            selectUser = (PreparedStatement) this.connection.prepareStatement(statement);
            selectUser.setLong(1, id);
            ResultSet resultSet = (ResultSet) selectUser.executeQuery();
            if (resultSet.next()) {
            	group = groupDAO.retrive(resultSet.getInt("id_group"));
                user = new User.Builder()
	        		.id(resultSet.getInt("id"))
	        		.name(resultSet.getString("name"))
					.login(resultSet.getString("login"))
					.password(resultSet.getString("password"))
					.group(group)
					.build();    
            }

        } catch (SQLException e) {
            log.error(GET_USER_ERROR_MSG + id);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        
        return user;
    }

    
    public Collection<User> retrive() throws ExceptionDAO {
        Collection<User> userList = new ArrayList<User>();
        try {
            User user = null;
            Group group = null;
            PreparedStatement selectUser = null;
            String statement = SELECT_QUERY;
            selectUser = (PreparedStatement) this.connection.prepareStatement(statement);
            ResultSet resultSet = (ResultSet) selectUser.executeQuery();
            while (resultSet.next()) {
            	group = groupDAO.retrive(resultSet.getInt("id_group"));
            	user = new User.Builder()
	        		.id(resultSet.getInt("id"))
	        		.name(resultSet.getString("name"))
					.login(resultSet.getString("login"))
					.password(resultSet.getString("password"))
					.group(group)
					.build(); 
                userList.add(user);
            }
        } catch (SQLException e) {
            log.error(GET_USER_LIST_ERROR_MSG);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        
        return userList;
    }

    
    public boolean delete(long id) throws ExceptionDAO {
        try {
            PreparedStatement deleteUser = null;
            String statement = DELETE_QUERY;
            deleteUser = (PreparedStatement) this.connection.prepareStatement(statement);
            deleteUser.setLong(1, id);
            int result = deleteUser.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error(DELETE_USER_ERROR_MSG + id);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        
        return false;
    }

    
    public boolean update(User user) throws ExceptionDAO {
        try {
            PreparedStatement updateUser = null;
            String statement = UPDATE_QUERY;
            updateUser = (PreparedStatement) this.connection.prepareStatement(statement);
            updateUser.setString(1, user.getName());
            updateUser.setString(2, user.getLogin());
            updateUser.setString(3, user.getPassword());
            updateUser.setInt(4, user.getGroup().getId());
            updateUser.setInt(5, user.getId());
            int result = updateUser.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error(UPDATE_USER_ERROR_MSG + user.getId());
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        } 
        
        return false;
    }

    
    public long create(User user) throws ExceptionDAO {
        long lastInsertID = 0;
        try {
            PreparedStatement insertUser = null;
            String statement = INSERT_QUERY;
            insertUser = (PreparedStatement) this.connection.prepareStatement(statement);
            insertUser.setString(1, user.getName());
            insertUser.setString(2, user.getLogin());
            insertUser.setString(3, user.getPassword());
            insertUser.setInt(4, user.getGroup().getId());
            int result = insertUser.executeUpdate();
            if (result != 0) {
                lastInsertID = insertUser.getLastInsertID();
            }
        } catch (SQLException e) {
            log.error(CREATE_USER_ERROR_MSG);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        
        return lastInsertID;
    }
}
