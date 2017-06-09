package client;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

import static client.Initialize.g;
import static client.Initialize.s;

/**
 * Created by Alpine Tree on 5/20/2017.
 */
public class Messaging{
    public static String[] history;
    private static int historyCounter;
    private static int historyBackcount;
    public String answer;
    BufferedReader in;
    public Messaging()throws IOException{
        history = new String[20];
        historyCounter = 0;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        checkMessage();
    }
    public void sendMessage(String message) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.println(message);
        g.println(message);
    }
    public static void sendMessage(String message,Socket s) throws IOException {
        if(command(message)){
            return;
        }
        if(Initialize.nick == null){
            Initialize.g.println(message);
            Messaging.addHistory(message);
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            out.println(message);
            return;
        }
        Initialize.g.println(Initialize.nick + ": " + message);
        Messaging.addHistory(message);
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.println(message);
    }
    public static boolean command(String message){
        String broken[] = message.split("\\s");
        if(broken[0].equalsIgnoreCase("/clear")){
            Initialize.g.clearTextArea();
            Initialize.g.println("   CLEARED");
            return true;
        }
        return false;
    }
    public static void addHistory(String hist){
        if(historyCounter == 19) {
            historyCounter = 0;
        }
            history[historyCounter] = hist;
            historyCounter++;
            historyBackcount = historyCounter -1;
    }
    public String history(){
        if(historyBackcount < 0){
            historyBackcount = historyCounter-1;
        }
        System.out.println(historyBackcount);
        String temp = history[historyBackcount];
        historyBackcount--;
        return temp;
    }
    public void checkMessage(){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                answer = null;
                while (true) {
                    try{
                        answer = in.readLine();
                    }catch(IOException e){

                    }
                    if(answer.contains("Nickname:")){
                        int length = answer.length();
                        try{
                            Initialize.setNick(answer.substring(9,length));
                        }catch(IOException e){

                        }
                        answer = null;
                    }
                    if (answer != null) {
                        if(answer.contains(":")){
                            notification();
                        }
                        System.out.println(answer);
                        g.println(answer);
                        answer = null;
                    }
                }
            }
        });
        t.start();
    }
    public void notification(){
        try {
            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource("client/blop.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
