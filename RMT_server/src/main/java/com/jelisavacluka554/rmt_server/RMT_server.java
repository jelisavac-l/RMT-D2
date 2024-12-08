
package com.jelisavacluka554.rmt_server;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.jelisavacluka554.rmt_server.db.DatabaseConnection;
import com.jelisavacluka554.rmt_server.ui.MainForm;
import java.sql.Connection;
import java.sql.SQLException;
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
        
        // Setting up a theme
        FlatIntelliJLaf.setup();
        
        // Save form in a variable so we can log;
        MainForm form = new MainForm();
        form.setVisible(true);
        form.log("Server started.");
        
        
    }
}
