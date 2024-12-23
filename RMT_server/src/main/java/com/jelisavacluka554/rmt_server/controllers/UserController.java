//   Copyright 2024. Luka Jelisavac
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License. 

package com.jelisavacluka554.rmt_server.controllers;

import com.jelisavacluka554.rmt_server.db.DatabaseConnection;
import com.jelisavacluka554.rmt_common.domain.User;
import java.sql.Connection;
import java.sql.Date;
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
                    rs.getString(7),
                    rs.getDate("birthday")
            ));
        }

        return users;
    }

    /**
     * Method that adds a new user to database. <br>
     * <b>WARNING</b> this method does <b>NOT</b> check user data, but only adds a new user.
     * Not to be confused with {@see addUserWithValidation}
     * @param u New (non-existing) user
     * @throws SQLException
     */
    public static void addUser(User u) throws SQLException {
        String query = "INSERT INTO rmt1.users(\n"
                + "firstname, lastname, jmbg, passport, username, pass, birthday)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, u.getFirstName());
        ps.setString(2, u.getLastName());
        ps.setString(3, u.getJmbg());
        ps.setString(4, u.getPassport());
        ps.setString(5, u.getUsername());
        ps.setString(6, u.getPassword());
        ps.setDate(7, new java.sql.Date(u.getBirthday().getTime()));
        System.out.println(ps);
        ps.executeUpdate();
        
    }

    public static void addUserWithValidation(User u) throws SQLException, IllegalArgumentException {

        // Check passport and JMBG
        String query = "SELECT * FROM rmt1.users WHERE jmbg=? AND passport=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, u.getJmbg());
        ps.setString(2, u.getPassport());
        Long foundID;
        String username;
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            foundID = rs.getLong("id");
            username = rs.getString("username");
        } else throw new IllegalArgumentException("Invalid jmbg/passport number.");
        if(username != null) throw new IllegalStateException("User already exists.");

        // Register user
        query = "UPDATE TABLE rmt1.users SET username=?, pass=? WHERE id=?";
        ps = conn.prepareStatement(query);
        ps.setString(1, u.getUsername());
        ps.setString(2, u.getPassword());
        ps.setLong(3, foundID);
        ps.executeUpdate();


    }
    
    /** 
     * Only to be used for logging in. (Registered users)
     * @param username
     * @param password
     * @return User object (if exists)
     * @throws SQLException if credentials don't match.
     */
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
                    rs.getString(7),
                    rs.getDate("birthday")
            );
        }
        return found;
    }

    /**
     * Only to be used for logging in. (Unregistered users)
     * @param jmbg
     * @param passport
     * @return User object (if exists)
     * @throws SQLException if credentials don't match. 
     */
    public static User getNonRegisteredUser(String jmbg, String passport) throws SQLException {
        User found = null;
        String query = "SELECT * FROM rmt1.users WHERE jmbg=? AND passport=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, jmbg);
        ps.setString(2, passport);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            found = new User(
                    rs.getLong("id"),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getDate("birthday")
            );
        }
        return found;
    }

}
