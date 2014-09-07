package com.pentalog.nguzun.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.pentalog.nguzun.common.ConnectionDB;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.vo.Role;

/**
 *
 * @author Guzun
 */
public class RoleDAO implements BaseDAO<Role>{

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

    private RoleDAO() {
        connection = ConnectionDB.getInstance();
    }

    public static RoleDAO getInstance() {
		if (instance == null) {
			instance = new RoleDAO();
		}
		return instance;
	}

    
    public Role retrive(long id) throws ExceptionDAO {
        Role role = null;
        try {
            PreparedStatement selectRole = null;
            String statement = SELECT_SINGLE_QUERY;
            selectRole = (PreparedStatement) this.connection.prepareStatement(statement);
            selectRole.setLong(1, id);
            ResultSet resultSet = (ResultSet) selectRole.executeQuery();
            if (resultSet.next()) {
            	role = new Role.Builder()
	        		.id(resultSet.getInt("id"))
	        		.name(resultSet.getString("name"))
					.description(resultSet.getString("description"))
					.build();
            }

        } catch (SQLException e) {
            log.error(GET_ROLE_ERROR_MSG + id);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        return role;
    }

    
    public Collection<Role> retrive() throws ExceptionDAO {
        Collection<Role> roleList = new ArrayList<Role>();
        try {
            Role role = null;
            PreparedStatement selectRole = null;
            String statement = SELECT_QUERY;
            selectRole = (PreparedStatement) this.connection.prepareStatement(statement);
            ResultSet resultSet = (ResultSet) selectRole.executeQuery();
            while (resultSet.next()) {
            	role = new Role.Builder()
	        		.id(resultSet.getInt("id"))
	        		.name(resultSet.getString("name"))
					.description(resultSet.getString("description"))
					.build();
                roleList.add(role);
            }
        } catch (SQLException e) {
            log.error(GET_ROLE_LIST_ERROR_MSG);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        return roleList;
    }

    
    public boolean delete(long id) throws ExceptionDAO {
        try {
            PreparedStatement deleteRole = null;
            String statement = DELETE_QUERY;
            deleteRole = (PreparedStatement) this.connection.prepareStatement(statement);
            deleteRole.setLong(1, id);
            int result = deleteRole.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error(DELETE_ROLE_ERROR_MSG + id);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        return false;
    }

    
    public boolean update(Role role) throws ExceptionDAO {
        try {
            PreparedStatement updateRole = null;
            String statement = UPDATE_QUERY;
            updateRole = (PreparedStatement) this.connection.prepareStatement(statement);
            updateRole.setString(1, role.getName());
            updateRole.setString(2, role.getDescription());
            updateRole.setInt(3, role.getId());
            int result = updateRole.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error(UPDATE_ROLE_ERROR_MSG + role.getId());
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }
        return false;
    }

    
    public long create(Role role) throws ExceptionDAO {
        long lastInsertID = 0;
        try {
            PreparedStatement insertRole = null;
            String statement = INSERT_QUERY;
            insertRole = (PreparedStatement) this.connection.prepareStatement(statement);
            insertRole.setString(1, role.getName());
            insertRole.setString(2, role.getDescription());
            int result = insertRole.executeUpdate();
            if (result != 0) {
                lastInsertID = insertRole.getLastInsertID();
            }
        } catch (SQLException e) {
            log.error(CREATE_ROLE_ERROR_MSG);
            throw new ExceptionDAO("SQL Exception " + e.getMessage(), log);
        }

        return lastInsertID;
    }
}
