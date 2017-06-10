package client;


import java.io.IOException;

/**
 * Created by Alpine Tree on 5/8/2017.
 */

/* TODO:
        ***Fixed Channel Issues by implementing Javas linked list instead of my own
        ***Fixed nick thing to only do it when the server responds with the nickname
        Just make things look better because the InNext method was made and now things are strange
        ***History works, but only goes up to 13? must check it out also can overwrite history in wrong order (Maybe use
        * a linked list instead
        Add ability for user to join seperate channel and create channels with passwords
 */
public class Main{
    public static void main(String[] args) throws IOException {
        Initialize i = new Initialize();
    }
}
