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
