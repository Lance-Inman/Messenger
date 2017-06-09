package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Alpine Tree on 5/15/2017.
 */
public class Users {
    public Socket s;
    BufferedReader in;
    String Nickname;
    Server server;
    Users u = this;
    public Users(Socket socket, Server server) throws IOException{
        this.s = socket;
        this.server = server;
        in = new BufferedReader((new InputStreamReader(s.getInputStream())));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        out.println("-----Welcome to Secure Line - Made by Keefer Sands------");
                        out.println("We are sending messages in plain text via use of a server");
                        out.println("----------------Please choose a nickname!----------------");
                        String temp = in.readLine();
                        if(server.checkUsername(temp)){
                            out.println("Username Already in use!");
                        }
                        else{
                            Nickname= temp;
                            break;
                        }
                    }

                    System.out.println("USER CONNECTED! With NickName: " + Nickname);
                    out.println("Currently online: " + server.onlineList(u));
                    getUserData();
                }catch(IOException e){

                }
            }
        });
        t.start();

    }
    public void getUserData() throws IOException {
        server.addUser(u);
        server.sendAll("Connected: " + Nickname,u);
        sendAndRecieve();

    }
    private void sendAndRecieve() throws IOException{
        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String response = in.readLine();
                        if(server.commands(response,u)){
                            response = null;
                            continue;
                        }
                        if (response != null) {
                            System.out.println(Nickname + ": " + response);
                            server.sendAll(Nickname + ": " + response, u);
                            response = null;
                        }

                    } catch (IOException e) {

                    }
                }
            }
        });
        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Scanner userIn = new Scanner(System.in);
                    try {
                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                        String reply = userIn.nextLine();
                        if (reply != null) {
                            out.println(reply);
                            reply = null;
                        }
                    } catch (IOException e) {

                    }
                }
            }
        });
        send.start();
        receive.start();
    }
}
