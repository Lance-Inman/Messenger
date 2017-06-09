package client;

import com.sun.org.omg.CORBA.InitializerSeqHelper;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Alpine Tree on 5/28/2017.
 */
public class Gui{
    private JButton sendButton;
    private JTextField textField2;
    private static JTextArea textArea1;
    private JPanel main;
    private JEditorPane editorPane1;
    public JFrame frame;
    String label = "Secure Line";
    public int counter = 0;
    String returnString;
    public Gui() {
        File f = new File("C:\\Users\\Alpine Tree\\IdeaProjects\\PGP Messenger\\src\\client\\SecureLine16x16.png");
        ImageIcon img = new ImageIcon(f.getPath());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Messaging.sendMessage("/disconnect", Initialize.s);
                } catch (IOException e) {

                } catch (NullPointerException n) {

                }

            }
        });
        Runtime.getRuntime().addShutdownHook(t);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = textField2.getText();
                    if(counter == 0){
                        returnString = text;
                        return;
                    }
                    Initialize.m.addHistory(textField2.getText());
                    Messaging.sendMessage(textField2.getText(),Initialize.s);
                    textField2.setText("");
                }catch(IOException a){
                    System.out.println("IOExeption Happened");
                }
                counter++;
            }
        });
        textField2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    textField2.setText(Initialize.m.history());
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        String text = textField2.getText();
                        if(counter == 0){
                            returnString = text;
                            textField2.setText("");
                            counter++;
                            return;
                        }
                        Messaging.sendMessage(text,Initialize.s);
                        textField2.setText("");
                    }catch(IOException a){
                        System.out.println("IOExeption Happened");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        frame = new JFrame(label);
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(main);
        frame.pack();
        frame.setVisible(true);

    }
    public void println(Object o){
        String s = o.toString();
        editorPane1.setText("   "+ editorPane1.getText() +"\n" + s);


   }
   public String inNext(){
        returnString = null;
        counter = 0;
        while(returnString == null){
            try{
                Thread.sleep(500);
            }catch(InterruptedException e){

            }
        }
        counter++;
        return returnString;
   }
   public void setLabel(String text){
        frame.setTitle(label + " - " + text);
   }
   public void clearTextArea(){
       editorPane1.setText("");
   }
}

