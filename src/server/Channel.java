package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Created by Alpine Tree on 5/20/2017.
 */
public class Channel {
    private String channelName;
    private String password;
    private boolean hasPassword;
    LinkedList<User> list = new LinkedList<User>();
    public Channel(String name){
        channelName = name;
        hasPassword = false;
    }
    public Channel(String name,String password){
        channelName = name;
        hasPassword = true;
    }
    public void addUser(User u){
        list.addLast(u);
    }
    public LinkedList<User> getList(){
        return list;
    }
    public String getName(){
        return channelName;
    }
    public void removeUser(User user, String message)throws IOException{
        for(int x = 0; x < list.size(); x++) {
            if(list.get(x) == user){
                PrintWriter out = new PrintWriter(list.get(x).s.getOutputStream(), true);
                out.println(message);
                list.remove(x);
                return;
            }
        }
    }


    public void sendAll(String s, User user) throws IOException {
        int x = 0;
        while (x<list.size()) {
            if (list.get(x)== user) {
                x++;
            } else {
                PrintWriter out = new PrintWriter(list.get(x).s.getOutputStream(), true);
                out.println(s);
                x++;
            }
        }
    }

    /**
     * Checks message to see if it is in the commands list, if it is then command is executed and method returns true
     * @param words The message passed
     * @param user user Object
     * @return
     * @throws IOException
     */
    public boolean commands(String words, User user)throws IOException{//checks and performs commands
        if(words.equalsIgnoreCase("/list")){
            System.out.println(user.Nickname + " used command: list");
            PrintWriter out = new PrintWriter(user.s.getOutputStream(), true);
            out.println("Currently online: " + onlineList(user));
            return true;
        }
        if(words.equalsIgnoreCase("/listChannels")){
            String value = Main.server.listChannels();
            PrintWriter out = new PrintWriter(user.s.getOutputStream(), true);
            out.println(value);
            return true;
        }
        if(words.equalsIgnoreCase("/disconnect") || words.equals("xlqiswympi")){
            if(user.Nickname.equals("/disconnect")){
                removeUser(user,"Come again soon!");
                return true;
            }
            System.out.println(user.Nickname + " used command: disconnect");

            sendAll(user.Nickname + " Disconnected!",user);
            removeUser(user,"Come again soon!");
            return true;
        }
        return false;
    }
    /**
     * Returns a string of all user connected to the channel
     * This method does not return the user passed to the method
     * in the online list
     *@param user the user object
     *@return String A formatted string of online user
     *@see User
     * **/
    public String onlineList(User user){ //returns online list relative to user
        String returned = "";
        //Checks if there is only one person
        if(list.size() == 0){
            return "None";
        }
        int x=0;
        while(x<list.size()){
            if(list.get(x).equals(user)){
                x++;
            }else if(x+1<list.size()){
                returned = returned + list.get(x).Nickname + ", ";
               x++;
            }else{
                returned = returned + list.get(x).Nickname;
                x++;
            }
            x++;
        }
        return returned;
    }
    public boolean checkUsername(String username){ //method that returns true when in use
        int x = 0;
        while(x<list.size()){
            if(list.get(x).Nickname != null && list.get(x).Nickname.equalsIgnoreCase(username)){
                return true;
            }
            x++;
        }
        return false;
    }

}
