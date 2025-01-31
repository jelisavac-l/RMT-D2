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
import com.jelisavacluka554.rmt_common.domain.Application;
import com.jelisavacluka554.rmt_common.domain.ApplicationItem;
import com.jelisavacluka554.rmt_common.domain.EUCountry;
import com.jelisavacluka554.rmt_common.domain.Transport;
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
                + "u.id AS uid, u.firstname, u.lastname, u.jmbg, u.passport, u.username, u.pass, u.birthday\n"
                + "FROM application a\n"
                + "JOIN transport t ON t.id = a.transport\n"
                + "JOIN users u ON u.id = a.userid WHERE a.userid=" + u.getId() + "\n"
                + "ORDER BY a.dateofentry ASC";

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
                    null,
                    rs.getDate("birthday"));
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
        System.out.println(a.getDateOfEntry());
        ps.setDate(3, new Date(a.getDateOfApplication().getTime()));
        ps.setDate(4, new Date(a.getDateOfEntry().getTime()));
        ps.setInt(5, a.getDuration());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        Long genId;
        if (rs.next()) {
            genId = rs.getLong(1);
        } else {
            throw new SQLException("Something went terribly wrong and if you see this, it is probably unfixable.");
        }

        System.out.println("Application added. Adding items...");

        // Then, add items
        var lai = a.getItems();
        for (var ai : lai) {

            // Da mi Gospod Bog oprosti za ovo.
            ai.setApplication(new Application(genId, null, null, null, null, null));
            addApplicationItem(ai);
            System.out.println("\t-> " + ai.getCountry().getName());
        }

        System.out.println("Items added.");
    }
    
    public static void deleteItems(Application apl) throws SQLException
    {
        String query = "DELETE FROM rmt1.applicationitem \n" +
                       "WHERE application=?";
        
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, apl.getId());
        ps.executeUpdate();
    }

    /**
     * Overwrites application apl1 with data from apl2.
     *
     * @param apl1 old application
     * @param apl2 new application
     * @throws SQLException
     */
    public static void updateApplication(Application apl1, Application apl2) throws SQLException {
        String query = "UPDATE rmt1.application a\n"
                + "	SET transport=?, dateofapplication=?, dateofentry=?, duration=?\n"
                + "	WHERE a.id=?;";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setLong(1, apl2.getTransport().getId());
        ps.setDate(2, new Date(apl2.getDateOfApplication().getTime()));
        ps.setDate(3, new Date(apl2.getDateOfEntry().getTime()));
        ps.setInt(4, apl2.getDuration());
        ps.setLong(5, apl1.getId());

        ps.executeUpdate();

        System.out.println("Application " + apl1.getId() + " updated, updating items..");
        deleteItems(apl1);
        var lai = apl2.getItems();
        for (var ai : lai) {

            // Da mi Gospod Bog oprosti za ovo.
            ai.setApplication(new Application(apl1.getId(), null, null, null, null, null));
            addApplicationItem(ai);
            System.out.println("\t-> " + ai.getCountry().getName());
        }

        System.out.println("Items added.");
    }
}
