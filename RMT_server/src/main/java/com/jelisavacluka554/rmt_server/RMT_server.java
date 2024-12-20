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
import com.jelisavacluka554.rmt_common.domain.Application;
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
import java.util.List;
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
        System.out.println("Setting up...");
        try {
            ServerSocket serverSocket = new ServerSocket(9554);
            System.out.println("Server started at port: " + serverSocket.getLocalPort());
//            RMT_server[] clientThreads = new RMT_server[10];

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepting a new connection.");
                
                new RMT_server(socket).start();
                
                //Check if there is a free thread
//                for (int i = 0; i < clientThreads.length; i++) {
//                    if (clientThreads[i] == null || !clientThreads[i].isAlive()) {
//                        clientThreads[i] = new RMT_server(socket);
//                        clientThreads[i].start();
//                        break;
//                    }
//                    System.err.println("Error: 0 threads available.");
//
//                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public RMT_server(Socket socket) {
        this.socket = socket;
        System.out.println("Initializing a thread for client: " + socket.getInetAddress());

    }

    /**
     * A method for managing incoming requests. Any thrown exceptions shall be
     * included in the exception parameter of the {@link Response} object and
     * transmitted to the client, signifying a failure in the operation.
     *
     * @param request
     * @return result of the requested operation.
     * @throws SQLException
     * @throws Exception
     */
    private synchronized Object handleRequest(Request request) throws SQLException, Exception {
        Object result = null;
        switch (request.getOperation()) {

            case PING: {
                result = "pong";
                break;
            }
            
            case MSG: {
                System.out.println(request.getArgument());
                break;
            }

            case LOGIN: {
                User user = (User) request.getArgument();
                System.out.println("Authentication for: " + user.getUsername() + " " + user.getPassword());
                User found = UserController.getUserFromCredentials(user.getUsername(), user.getPassword());
                if (found == null) {
                    throw new Exception("User not found!");
                } else {
                    result = found;
                }
                break;
            }

            case REGISTER: {
                User user = (User) request.getArgument();
                System.out.println(user);

                if (!validateUser(user)) {
                    throw new Exception("Incorrect credentials!");
                }

                System.out.println("Registering a new user.");
                try {
                    UserController.addUser(user);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    throw new Exception("Registration failed: " + ex.getMessage());
                } finally {
                    result = user + " added.";
                }
                break;
            }

            case APPL_GET_LIST: {
                User user = (User) request.getArgument();
                List<Application> la = ApplicationController.getAllApplications(user);
                result = la;
                break;
            }

            case APPL_CREATE: {
                Application application = (Application) request.getArgument();
                ApplicationController.addApplication(application);
                break;
            }

            case APPL_UPDATE: {

                break;
            }
            
            case EUC_GET_LIST: {
                
                break;
            }
            
            case T_GET_LIST: {
                
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
                response = new Response();
                System.out.println(socket.getInetAddress() + " requested " + request.getOperation());

                // Handling a STOP signal.
                if (request.getOperation() == Operation.STOP) {
                    System.out.println("Ending connection with " + socket.getInetAddress());
                    socket.close();
                    return;
                }

                try {
                    result = handleRequest(request);
                } catch (Exception e) {
                    response.setException(e);
                }

                response.setResult(result);

                try {
                    sender.send(response);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

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

    private boolean validateUser(User user) {
        return true;
        // Do validation later.
    }
}
