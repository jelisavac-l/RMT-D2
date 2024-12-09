package com.jelisavacluka554.rmt_server.controllers;

import com.jelisavacluka554.rmt_server.db.DatabaseConnection;
import com.jelisavacluka554.rmt_server.domain.Application;
import com.jelisavacluka554.rmt_server.domain.ApplicationItem;
import com.jelisavacluka554.rmt_server.domain.EUCountry;
import com.jelisavacluka554.rmt_server.domain.Transport;
import com.jelisavacluka554.rmt_server.domain.User;
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
public class ApplicationController {

    public static List<ApplicationItem> getAllApplicationItems(Application a) throws SQLException {
        var lai = new LinkedList<ApplicationItem>();
        String query = "SELECT * FROM applicationitem ai\n"
                + "JOIN eucountry e ON e.id = ai.country WHERE ai.application=" + a.getId();
        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            lai.add(new ApplicationItem(
                    a,
                    rs.getLong(2),
                    new EUCountry(
                            rs.getLong("id"),
                            rs.getString("name")
                    )
            ));
        }
        return lai;
    }

    /**
     * Returns all applications
     *
     * @return List of all applications from the database.
     * @throws SQLException
     */
    public static List<Application> getAllApplications() throws SQLException {
        List<Application> la = new LinkedList<>();
        String query = "SELECT \n"
                + "a.id AS aid, a.dateofapplication, a.dateofentry, a.duration,\n"
                + "t.id AS tid, t.name AS transport,\n"
                + "u.id AS uid, u.firstname, u.lastname, u.jmbg, u.passport, u.username, u.pass\n"
                + "FROM application a\n"
                + "JOIN transport t ON t.id = a.transport\n"
                + "JOIN users u ON u.id = a.userid";

        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            Transport ttransport = new Transport(rs.getLong("tid"), rs.getString("transport"));
            User tuser = new User(rs.getLong("uid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("jmbg"),
                    rs.getString("passport"),
                    rs.getString("username"),
                    null);  // Safety null
            Application tapplication = new Application(
                    rs.getLong("aid"),
                    tuser,
                    ttransport,
                    rs.getDate("dateofapplication"),
                    rs.getDate("dateofentry"),
                    rs.getInt("duration"));
            tapplication.setItems(getAllApplicationItems(tapplication));
            la.add(tapplication);
        }
        return la;
    }

    /**
     * Returns users applications;
     *
     * @param u User
     * @return List of all applications registered by User u.
     * @throws SQLException
     */
    public static List<Application> getAllApplications(User u) throws SQLException {
        List<Application> la = new LinkedList<>();
        String query = "SELECT \n"
                + "a.id AS aid, a.dateofapplication, a.dateofentry, a.duration,\n"
                + "t.id AS tid, t.name AS transport,\n"
                + "u.id AS uid, u.firstname, u.lastname, u.jmbg, u.passport, u.username, u.pass\n"
                + "FROM application a\n"
                + "JOIN transport t ON t.id = a.transport\n"
                + "JOIN users u ON u.id = a.userid WHERE a.userid=" + u.getId();

        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            Transport ttransport = new Transport(rs.getLong("tid"), rs.getString("transport"));
            User tuser = new User(rs.getLong("uid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("jmbg"),
                    rs.getString("passport"),
                    rs.getString("username"),
                    null);  // Safety null
            Application tapplication = new Application(
                    rs.getLong("aid"),
                    tuser,
                    ttransport,
                    rs.getDate("dateofapplication"),
                    rs.getDate("dateofentry"),
                    rs.getInt("duration"));
            tapplication.setItems(getAllApplicationItems(tapplication));
            la.add(tapplication);
        }
        return la;
    }

    public static void addApplicationItem(ApplicationItem ai) throws SQLException {
        String query = "INSERT INTO rmt1.applicationitem(\n"
                + "application, serialnumber, country)\n"
                + "VALUES (?, ?, ?);";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, ai.getApplication().getId());
        ps.setLong(2, ai.getId());
        ps.setLong(3, ai.getCountry().getId());
        ps.executeUpdate();
    }

    public static void addApplication(Application a) throws SQLException {
        
        // Add application first
        String query = "INSERT INTO rmt1.application(\n"
                + "userid, transport, dateofapplication, dateofentry, duration)\n"
                + "VALUES (?, ?, ?, ?, ?);";
        
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, a.getUser().getId());
        ps.setLong(2, a.getTransport().getId());
        ps.setDate(3, (Date) a.getDateOfApplication());
        ps.setDate(4, (Date) a.getDateOfEntry());
        ps.setInt(5, a.getDuration());
        
        ps.executeUpdate();
        
        ResultSet rs = ps.getGeneratedKeys();
        Long genId;
        if(rs.next())
            genId = rs.getLong(1);
        else throw new SQLException("Something went terribly wrong and if you see this, it is probably unfixable.");

        System.out.println("Application added. Adding items...");
        
        // Then, add items
        var lai = a.getItems();
        for(var ai : lai) {
            addApplicationItem(ai);
            System.out.println("\t-> " + ai.getCountry().getName());
        }
        
        System.out.println("Items added.");
    }
}
