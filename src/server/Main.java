package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Alpine Tree on 5/8/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        ServerSocket listener = new ServerSocket(9056);
        while (true) {
            Socket socket = listener.accept();
            Users u = new Users(socket, server);
            //server.addUser(new Users(socket,server));
        }
    }
}
