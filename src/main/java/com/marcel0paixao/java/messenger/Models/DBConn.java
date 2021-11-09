package com.marcel0paixao.java.messenger.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConn {
    
    public Connection DBConnect() throws SQLException, ClassNotFoundException{
        Connection conn = null;
        String userName = null;
        String password = null;
        String url = null;
        String jdbcDriver = null;

        userName = "root";
        password = "";
        jdbcDriver = "com.mysql.jdbc.Driver";

        url = "jdbc:mysql://localhost:3306/messenger";

        Class.forName(jdbcDriver);
        
        try{
            conn = DriverManager.getConnection(url, userName, password);
        } catch(SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
        return conn;
    }
    
}
