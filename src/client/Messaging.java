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
    public String answer;
    BufferedReader in;
    public Messaging()throws IOException{
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        checkMessage();
    }
    public void sendMessage(String message) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.println(message);
        g.println(message);
    }
    public static void sendMessage(String message,Socket s) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(),true);
        out.println(message);
        g.println(message);
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
