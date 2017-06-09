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
public class User {
    public Socket s;
    BufferedReader in;
    String Nickname;
    Channel channel;
    User u = this;
    PrintWriter out;
    public User(Socket socket, Channel channel) throws IOException{
        this.s = socket;
        this.channel = channel;
        in = new BufferedReader((new InputStreamReader(s.getInputStream())));
        out = new PrintWriter(s.getOutputStream(), true);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        out.println("-----Welcome to Secure Line - Made by Keefer Sands------");
                        out.println("We are sending messages in plain text via use of a channel");
                        out.println("----------------Please choose a nickname!----------------");
                        String temp = in.readLine();
                        if(Main.server.checkUsername(temp)){
                            out.println("Username Already in use!\n\n\n");
                        }
                        else{
                            Nickname= temp;
                            break;
                        }
                    }
                    getUserData();
                }catch(IOException e){
                }
            }
        });
        t.start();

    }
    public void getUserData() throws IOException {
        if(channel.commands(Nickname,this)){
            return;
        }
        System.out.println("USER CONNECTED! With NickName: " + Nickname);
        out.println("Nickname:" + Nickname);
        out.println("Currently online: " + channel.onlineList(u));
        channel.addUser(u);
        channel.sendAll("Connected: " + Nickname,u);
        sendAndRecieve();

    }
    private void sendAndRecieve() throws IOException{
        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String response = in.readLine();
                        if(channel.commands(response,u)){
                            response = null;
                            continue;
                        }
                        if (response != null) {
                            System.out.println(Nickname + ": " + response);
                            channel.sendAll(Nickname + ": " + response, u);
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
