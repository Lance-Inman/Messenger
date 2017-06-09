package client;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Alpine Tree on 6/1/2017.
 */
public class Initialize {
    public static Socket s;
    public static Gui g;
    public String serverAddress;
    public static String nick;
    public static Messaging m;
    public Initialize(){
        g = new Gui();
            try {
                g.println("   Please enter server address (ex: 127.0.0.1)");
                System.out.println("Please enter server address (ex: 127.0.0.1)");;
                serverAddress = g.inNext();
                s = new Socket(serverAddress, 9056);
                System.out.println("Connected!");
                m = new Messaging();

            } catch (SocketException e) {
                System.out.println("FAILED TO CONNECT: TRY AGAIN");
                g.println("FAILED TO CONNECT: TRY AGAIN");
            }catch(IOException f){

            }
    }
    public static void setNick(String nickName) throws IOException{
        nick = nickName;
        g.setLabel(nickName);
    }
}
