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

package com.jelisavacluka554.rmt_server;

import com.jelisavacluka554.rmt_common.communication.Request;
import com.jelisavacluka554.rmt_common.communication.Operation;
import com.jelisavacluka554.rmt_common.communication.Receiver;
import com.jelisavacluka554.rmt_common.communication.Response;
import com.jelisavacluka554.rmt_common.communication.Sender;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.jelisavacluka554.rmt_server.controllers.*;
import com.jelisavacluka554.rmt_server.db.DatabaseConnection;
import com.jelisavacluka554.rmt_common.domain.User;
import com.jelisavacluka554.rmt_server.ui.MainForm;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
public class RMT_server extends Thread {

    private final int PORT = 9554;
    private Sender sender;
    private Receiver receiver;
    private Socket socket;

    public static void main(String[] args) {
        System.out.println("Server started!");
        try {
            ServerSocket serverSocket = new ServerSocket(9554);
            Socket socket = serverSocket.accept();
            RMT_server server = new RMT_server(socket);
            server.run();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public RMT_server(Socket socket) {
        this.socket = socket;
        System.out.println("Initializing a thread for client: " + socket.getInetAddress());

    }

    private synchronized Object handleRequest(Request request) throws SQLException, Exception {
        Object result = null;
        switch (request.getOperation()) {

            case PING: {
                System.out.println(socket.getInetAddress() + " pinged.");
                result = "pong";
                break;
            }

            case LOGIN: {
                User user = (User) request.getArgument();
                System.out.println(user.getUsername() + " " + user.getPassword());
                User found = UserController.getUserFromCredentials(user.getUsername(), user.getPassword());
                if(found == null) {
                    throw new Exception("User not found!");
                } else result = found;
                break;
            }

            case REGISTER: {

                break;
            }

            case APPL_GET_LIST: {

                break;
            }

            case APPL_CREATE: {

                break;
            }

            case APPL_UPDATE: {

                break;
            }
        }
        return result;
    }

    @Override
    public void run() {
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket);
        Response response = new Response();
        Object result = null;

        try {

            while (true) {
                Request request = (Request) receiver.receive();

                // Handling a STOP signal.
                if (request.getOperation() == Operation.STOP) {
                    System.out.println("Ending connection with " + socket.getInetAddress());
                    socket.close();
                    return;
                }

                response.setResult(handleRequest(request));
                try {
                    sender.send(response);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
                
                System.out.println(socket.getInetAddress() + " requested " + request.getOperation());
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            response.setException(ex);
            try {
                    sender.send(response);
                } catch (Exception ex1) {
                    System.err.println(ex1.getMessage());
                }
        }
    }
}
