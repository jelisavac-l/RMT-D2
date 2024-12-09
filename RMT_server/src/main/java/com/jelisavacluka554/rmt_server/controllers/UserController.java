package com.jelisavacluka554.rmt_server.controllers;

import com.jelisavacluka554.rmt_server.db.DatabaseConnection;
import com.jelisavacluka554.rmt_server.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luka
 */
public class UserController {

    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new LinkedList<>();

        String query = "SELECT * FROM rmt1.users";
        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            users.add(new User(
                    rs.getLong("id"),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            ));
        }

        return users;
    }

    public static void addUser(User u) throws SQLException {
        String query = "INSERT INTO rmt1.users(\n"
                + "firstname, lastname, jmbg, passport, username, pass)\n"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, u.getFirstName());
        ps.setString(2, u.getLastName());
        ps.setString(3, u.getJmbg());
        ps.setString(4, u.getPassport());
        ps.setString(5, u.getUsername());
        ps.setString(6, u.getPassword());
        
        ps.executeUpdate();
        
    }
    
    
    public static User getUserFromCredentials(String username, String password) throws SQLException {
        
        User found = null;
        String query = "SELECT * FROM rmt1.users WHERE username=? AND pass=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            found = new User(
                    rs.getLong("id"),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            );
        }
        return found;
    }

}
