/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcel0paixao.java.messenger.Models;

import com.marcel0paixao.java.messenger.Controllers.ClientController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author celom
 */
public class User {
    public ResultSet auth(ClientController clientController) throws SQLException, ClassNotFoundException{
        Connection conn = new DBConn().DBConnect();
        
        try{
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            pstm.setString(1, clientController.getEmail());
            pstm.setString(2, clientController.getPass());
            
            ResultSet rs = pstm.executeQuery();
            return rs;
        } catch(SQLException error){
            JOptionPane.showMessageDialog(null, "Error ClientController: " + error);
            return null;
        }
    }
}
