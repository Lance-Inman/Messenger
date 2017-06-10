package server;

import java.util.LinkedList;

/**
 * Created by Alpine Tree on 6/4/2017.
 */
public class Server {
    LinkedList<Channel> channelList = new LinkedList();
    public Server(){

    }
    public void addChannel(Channel ch){
        channelList.addLast(ch);
    }
    public void removeChannel(Channel ch){
        int x = 0;
        while(x<channelList.size()){
            if(channelList.get(x)==ch){
                channelList.remove(x);
            }
        }
    }
    public boolean checkUsername(String username){ //method that returns true when in use
        int x = 0;
        int y = 0;
        while(x<channelList.size()){
            int temp = channelList.get(x).list.size();
            while(y<temp){
                if(channelList.get(x).list.get(y).Nickname != null && channelList.get(x).list.get(y).Nickname.equalsIgnoreCase(username)){
                    return true;
                }
                y++;
            }
            x++;
        }
        return false;
    }
    public String listChannels(){
        String returned = "Channels:";
        int x=0;
        if(channelList.size()== 0){
            returned = returned + " " + channelList.get(x).getName();
            return returned;
        }
        while(x<channelList.size()){
            if(x+1 > channelList.size()){
                returned = returned + " " + channelList.get(x).getName() + ",";
                x++;
            }
            else{
                returned = returned + " " + channelList.get(x).getName();
                x++;
            }

        }
        return returned;
    }

}