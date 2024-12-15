
package com.jelisavacluka554.rmt_client;

// TODO: MOVE DOMAIN AND COMMUNICATION TO "COMMON"

import com.jelisavacluka554.rmt_common.domain.*;
import com.jelisavacluka554.rmt_common.communication.*;
import java.net.Socket;

/**
 *
 * @author luka
 */
public class RMT_client {
    private static Socket socket;
    private static Sender sender;
    private static Receiver receiver;
    
    public static void main(String[] args) throws Exception {
        User test = new User(null, null, null, null, null, "admin", "admin");

        System.out.println("Hello World!");
        socket = new Socket("127.0.0.1", 9554);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
//        
//        
        Request request = new Request(Operation.LOGIN, test);
        sender.send(request);
        Response ans = (Response) receiver.receive();
        User u = (User)ans.getResult();
        System.out.println(u);
        request = new Request(Operation.STOP, null);
        sender.send(request);
    }
}
