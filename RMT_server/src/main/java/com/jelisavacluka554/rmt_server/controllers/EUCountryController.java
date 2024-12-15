package com.jelisavacluka554.rmt_server.controllers;

import com.jelisavacluka554.rmt_server.db.DatabaseConnection;
import com.jelisavacluka554.rmt_common.domain.EUCountry;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luka
 */
public class EUCountryController {

    public static List<EUCountry> getAllEUCountries() throws SQLException {

        List<EUCountry> lt = new LinkedList<EUCountry>();
        String query = "SELECT id, name\n"
                + "FROM rmt1.eucountry;";

        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            lt.add(new EUCountry(
                    rs.getLong(1),
                    rs.getString(2)
            ));
        }

        return lt;
    }
}
