package server;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Alpine Tree on 5/20/2017.
 */
public class Server {
    public class Node{
        Node next;
        Users user;
        public Node(Users s){
            this.user = s;
            Node next = null;
        }
    }
    Node first;
    Node last;
    public void addUser(Users users){
       // printList();
        if(first == null){
            first = new Node(users);
            return;
        }
        if(first.next == null){
            Node pointer = first;
            pointer.next = new Node(users);
            last = pointer.next;
        }
        else if(first != null){
            Node pointer = last;
            pointer.next = new Node(users);
            last = pointer.next;
        }
    }
    public void printList(){
        if(first == null){
            return;
        }
        Node pointer = first;
        while(pointer.next != null){
            System.out.println(pointer.user.Nickname);
            pointer = pointer.next;
        }
        System.out.println(pointer.user.Nickname);
    }
    public boolean checkUsername(String username){ //method that returns true when in use
        int x = 0;
        Node pointer = first;
        while(pointer != null){
            if(pointer.user.Nickname != null && pointer.user.Nickname.equalsIgnoreCase(username)){
                return true;
            }
            pointer = pointer.next;
            if(pointer == first){
                return false;
            }
        }
        return false;
    }
    public int checkPermissions(String username){
        if(username.equalsIgnoreCase("Keefer")){
            return 10;
        }
        return 0;
    }

    public void sendAll(String s, Users users) throws IOException {
        Node pointer = first;
        while (pointer != null) {
            if (pointer.user.equals(users)) {
                pointer = pointer.next;
            } else {
                PrintWriter out = new PrintWriter(pointer.user.s.getOutputStream(), true);
                out.println(s);
                pointer = pointer.next;
            }
        }
    }
    public boolean commands(String words, Users user)throws IOException{//checks and performs commands
        if(words.equalsIgnoreCase("/list")){
            System.out.println(user.Nickname + " used command: list");
            PrintWriter out = new PrintWriter(user.s.getOutputStream(), true);
            out.println("Currently online: " + onlineList(user));
            return true;
        }
        if(words.equalsIgnoreCase("/disconnect") || words.equals("xlqiswympi")){
            System.out.println(user.Nickname + " used command: disconnect");
            sendAll(user.Nickname + " Disconnected!",user);
            removeUser(user,"Come again soon!");
            return true;

        }
        return false;
    }
    public String onlineList(Users users){ //returns online list relative to user
        Node pointer = first;
        String returned = "";
        //Checks if there is only one person
        if(pointer == null){
            return "None";
        }
        while(pointer != null){
            if(pointer.user.equals(users)){
                pointer = pointer.next;
            }else if(pointer.next != null){
                returned = returned + pointer.user.Nickname + ", ";
                pointer = pointer.next;
            }else{
                returned = returned + pointer.user.Nickname;
                pointer = pointer.next;
            }
        }
        return returned;
    }
    public int listLength(){
        int counter = 0;
        Node pointer = first;
        while(pointer.next != null){
            pointer = pointer.next;
            counter++;
        }
        counter++;
        return counter;
    }
    public void removeUser(Users user, String message)throws IOException{
        Node fpointer = first;
        if(fpointer.next == null){
            PrintWriter out = new PrintWriter(fpointer.user.s.getOutputStream(),true);
            first = null;
            out.println(message);
            fpointer.user.s.close();
            return;
        }
        else{
            /*if(fpointer.user.equals(user)){
                fpointer.user.s.close();
                first = fpointer.next;
                return;
            }*/
            while(fpointer.next != null) {
                if (fpointer.next.user.equals(user)) {
                    PrintWriter out = new PrintWriter(fpointer.next.user.s.getOutputStream(), true);
                    out.println(message);
                    fpointer.next.user.s.close();
                    fpointer.next = fpointer.next.next;
                    return;
                }
            }
        }
    }
}
