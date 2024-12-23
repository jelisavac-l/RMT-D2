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
package com.jelisavacluka554.rmt_client;

// TODO: MOVE DOMAIN AND COMMUNICATION TO "COMMON"
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.jelisavacluka554.rmt_common.domain.*;
import com.jelisavacluka554.rmt_common.communication.*;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author luka
 */
public class RMT_client {

    private static Socket socket;
    private static Sender sender;
    private static Receiver receiver;

    // Primitive audit
    private static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        if (RMT_client.loggedUser == null) {
            RMT_client.loggedUser = loggedUser;
        }
    }

    public static void main(String[] args) throws Exception {
        FlatIntelliJLaf.setup();
        try {
            init();
            pingServer();

            new FormLogin().setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

    }

    private static void init() throws IOException {
        try {
            socket = new Socket("127.0.0.1", 9554);
            sender = new Sender(socket);
            receiver = new Receiver(socket);
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static void disconnect() throws Exception {
        try {
            System.out.println("Disconnectiong...");
            sender.send(new Request(Operation.STOP, null));
            System.out.println("Disconnected!");
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static void pingServer() throws Exception {
        try {
            sender.send(new Request(Operation.PING, null));
            Response response = (Response) receiver.receive();
            System.out.println(response.getResult());
        }  catch (SocketException e) {
        JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
        }
    }
   

    public static User login(String username, String password) throws Exception {
        try {
            User user = new User(null, null, null, null, null, username, password);
            Request request = new Request(Operation.LOGIN, user);
            sender.send(request);
            Response response = (Response) receiver.receive();
            if (response.getException() != null) {
                JOptionPane.showMessageDialog(null, response.getException().getMessage(), "System", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            User newUser = (User) response.getResult();
            System.out.println(newUser);
            getApplicationList(newUser);
            return newUser;
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return null;
    }

    public static boolean register(User user) throws Exception {
        try {
            Request request = new Request(Operation.REGISTER, user);
            sender.send(request);
            Response response = (Response) receiver.receive();
            if (response.getException() != null) {
                JOptionPane.showMessageDialog(null, response.getException().getMessage(), "System", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                JOptionPane.showMessageDialog(null, user + " has been registered!", "System", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return false;
    }

    public static List<Application> getApplicationList(User u) throws Exception {
        try {
            Request request = new Request(Operation.APPL_GET_LIST, u);
            sender.send(request);
            Response response = (Response) receiver.receive();

            for (var ux : (List<Application>) response.getResult()) {
                System.out.println(ux.toString());
            }
            if (response.getException() != null) {
                JOptionPane.showMessageDialog(null, response.getException().getMessage(), "System", JOptionPane.ERROR_MESSAGE);
            }
            return (List<Application>) response.getResult();
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return null;
    }

    public static List<EUCountry> getEUCountryList() throws Exception {
        try {
            Request request = new Request(Operation.EUC_GET_LIST, null);
            sender.send(request);
            Response response = (Response) receiver.receive();
            if (response.getException() != null) {
                JOptionPane.showMessageDialog(null, response.getException().getMessage(), "System", JOptionPane.ERROR_MESSAGE);
            }
            return (List<EUCountry>) response.getResult();
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return null;

    }

    public static List<Transport> getTransportList() throws Exception {
        try {
            Request request = new Request(Operation.T_GET_LIST, null);
            sender.send(request);
            Response response = (Response) receiver.receive();
            if (response.getException() != null) {
                JOptionPane.showMessageDialog(null, response.getException().getMessage(), "System", JOptionPane.ERROR_MESSAGE);
            }
            return (List<Transport>) response.getResult();
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return null;
    }

    public static void createApplication(Application a) throws Exception {
        try {
            Request request = new Request(Operation.APPL_CREATE, a);
            sender.send(request);
            Response response = (Response) receiver.receive();
            if (response.getException() != null) {
                JOptionPane.showMessageDialog(null, response.getException().getMessage(), "MOLIM TE", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println(response.getResult());
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static void updateApplication(Application apl1, Application apl2) throws Exception {
        try {
            List<Application> la = new LinkedList<>();
            la.add(apl1);
            la.add(apl2);
            Request request = new Request(Operation.APPL_UPDATE, la);
            sender.send(request);
            Response response = (Response) receiver.receive();
            if (response.getException() != null) {
                JOptionPane.showMessageDialog(null, response.getException().getMessage(), "MOLIM TE", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println(response.getResult());
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Connection refused.", "System", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
