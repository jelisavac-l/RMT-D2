
package com.jelisavacluka554.rmt_client;

// TODO: MOVE DOMAIN AND COMMUNICATION TO "COMMON"

import com.jelisavacluka554.rmt_server.communication.Operation;
import com.jelisavacluka554.rmt_server.communication.Receiver;
import com.jelisavacluka554.rmt_server.communication.Request;
import com.jelisavacluka554.rmt_server.communication.Response;
import com.jelisavacluka554.rmt_server.communication.Sender;
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
        System.out.println("Hello World!");
        socket = new Socket("127.0.0.1", 9554);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        Request request = new Request(Operation.PING, (Object)3);
        sender.send(request);
        Response ans = (Response) receiver.receive();
        System.out.println(ans.getResult());
        request = new Request(Operation.STOP, null);
        sender.send(request);
    }
}
