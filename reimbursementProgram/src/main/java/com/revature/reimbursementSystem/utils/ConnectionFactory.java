package com.revature.reimbursementSystem.utils;
//purpose of this class is to bridge the DAO classes with our DB

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*This is a singleton design pattern
* singleton design patterns are used to make sure that a class only has one instance,
* while providing a global access point to this instance*/
public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;


    /*load in jdbc*/
    static{
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //class to read the properties file in /main/resources/
    private final Properties props = new Properties();
    //the singleton constructor should be private in order to avoid generating more than one instance
    /*This is to prevent construction calls with the new operator outside this class*/
    private ConnectionFactory(){
        try{
            props.load(new FileReader("reimbursementProgram/src/main/resources/db.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //static method that controls access to singleton instance
    public static ConnectionFactory getInstance(){
        /*Makes sure that an instance of the class has not already been made.*/
        if (connectionFactory == null) connectionFactory = new ConnectionFactory();
        return connectionFactory;
    }
    /*use props.getProperty to access properties and pass them to the DriverManager in java.sql package
    * */
    public Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(props.getProperty("url"),props.getProperty("username"),props.getProperty("password"));
        if (con == null) throw new RuntimeException("Could not establish DB connection");
        else return con;
    }
}
