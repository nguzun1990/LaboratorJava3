package com.pentalog.nguzun.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

/**
 *
 * @author Guzun
 */

public class ConnectionDB {

    final static Properties prop = new Properties();
    
    private static Connection connection = null;
    
    private static final Logger log = Logger.getLogger(ConnectionDB.class.getName());

    public static Connection getInstance() {
        try {
            prop.load(new FileInputStream("src/main/resources/jdbc.properties"));
        } catch (IOException ex) {
            log.error(ex);
        }
        String host = prop.getProperty("jdbc.host");
        String port = prop.getProperty("jdbc.port");
        String database = prop.getProperty("jdbc.database");
        String username = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        if (connection == null) {
            new ConnectionDB(host, port, database, username, password);
        }
        return connection;
    }

    private ConnectionDB(String host, String port, String database, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("MySQL JDBC Driver - not found", e);
            e.printStackTrace();
            return;
        }

        try {
            String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;
            System.out.println(connectionString);
            connection = (Connection) DriverManager.getConnection(connectionString, username, password);

        } catch (SQLException e) {
            log.error("Connection Failed!", e);
            e.printStackTrace();
            return;
        }
        if (connection == null) {
            log.error("Failed to make connection!");
            System.out.println("Failed to make connection!");
        }

    }
}
