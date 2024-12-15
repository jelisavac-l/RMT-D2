package com.jelisavacluka554.rmt_server.controllers;

import com.jelisavacluka554.rmt_server.db.DatabaseConnection;
import com.jelisavacluka554.rmt_common.domain.Transport;
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
public class TransportController {

    public static List<Transport> getAllTransports() throws SQLException {
        
        List<Transport> lt = new LinkedList<Transport>();
        String query = "SELECT id, name\n"
                + "FROM rmt1.transport;";
        
        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        while(rs.next()) {
            lt.add(new Transport(
                    rs.getLong(1),
                    rs.getString(2)
            ));
        }
        
        return lt;
    }

}
