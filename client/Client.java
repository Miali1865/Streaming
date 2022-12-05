package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.awt.*;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import player.*;

public class Client {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException,UnsupportedAudioFileException, LineUnavailableException, JavaLayerException, ClassNotFoundException {
    
        Socket clientSocket = new Socket("localhost",4000);
        // DataInputStream data = new DataInputStream(clientSocket.getInputStream());

        // int len = 1000000;
        // byte[] mybytearray = new byte[len];
        // String filename = data.readUTF();
        // System.out.println(filename);

        // while(true) {
        //     data.read(mybytearray, 0, len);
        //     System.out.println("en cours de lecture...");
        //     Thread play = new Thread(new PlayMP3(mybytearray));
        //     play.start();
        //     play(mybytearray);
        // }

        InputStream inputStream=clientSocket.getInputStream();
        System.out.println("Reading"+System.currentTimeMillis());
        byte[] sizear=new byte[4];
        inputStream.read(sizear);
        int size=ByteBuffer.wrap(sizear).asIntBuffer().get();
        byte[] imagear=new byte[size];
        inputStream.read(imagear);
        BufferedImage image=ImageIO.read(new ByteArrayInputStream(imagear));
        ImageIcon imageIcon = new ImageIcon(image);
        JFrame jFrame = new JFrame();
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(500,500);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        jFrame.add(jLabel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("image recu");

    }

    public static void play(byte[] data) throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        Player player = new Player(in);
        player.play();
    }
}
