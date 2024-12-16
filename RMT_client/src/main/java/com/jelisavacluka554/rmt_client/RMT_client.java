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

    public static void main(String[] args) throws Exception {
        FlatIntelliJLaf.setup();
        try {
            init();
            pingServer();

            new FormLogin().setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "System", JOptionPane.ERROR_MESSAGE);
        }

    }

    private static void init() throws IOException {
        socket = new Socket("127.0.0.1", 9554);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    public static void disconnect() throws Exception {
        System.out.println("Disconnectiong...");
        sender.send(new Request(Operation.STOP, null));
        System.out.println("Disconnected!");
    }

    // TEST METHODS

    public static void pingServer() throws Exception {
        sender.send(new Request(Operation.PING, null));
        Response response = (Response) receiver.receive();
        System.out.println(response.getResult());
    }

    public static User login(String username, String password) throws Exception {
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
        return newUser;
    }
}
