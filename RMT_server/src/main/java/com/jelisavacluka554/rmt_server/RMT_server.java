package com.jelisavacluka554.rmt_server;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.jelisavacluka554.rmt_server.controllers.UserController;
import com.jelisavacluka554.rmt_server.db.DatabaseConnection;
import com.jelisavacluka554.rmt_server.domain.User;
import com.jelisavacluka554.rmt_server.ui.MainForm;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author luka
 */
public class RMT_server {

    private final int PORT = 9554;

    public static void main(String[] args) {

        try {
            // Setting up a theme
//        FlatIntelliJLaf.setup();

// Save form in a variable so we can log;
//        MainForm form = new MainForm();
//        form.setVisible(true);
//        form.log("Server started.");
            List<User> lu = UserController.getAllUsers();
            
            for(var u : lu)
            {
                System.out.println(u);
            }
            
            
            
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(RMT_server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
