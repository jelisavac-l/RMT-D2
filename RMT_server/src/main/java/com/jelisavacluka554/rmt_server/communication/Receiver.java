package com.jelisavacluka554.rmt_server.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luka
 */
public class Receiver {
    private final Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }
    
    public Object receive() throws Exception{
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (IOException ex) {
             ex.printStackTrace();
             throw new Exception("Greska kod citanja objekta: " + ex.getMessage());
        }
       
    }
}
