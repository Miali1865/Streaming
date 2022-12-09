package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.awt.*;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.binding.lib.LibVlc;
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.fullscreen.windows.Win32FullScreenStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.*;
import java.lang.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import player.*;

public class Client {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;


    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException,UnsupportedAudioFileException, LineUnavailableException, JavaLayerException, ClassNotFoundException {
    
        Socket clientSocket = new Socket("localhost",4000);
        DataInputStream data = new DataInputStream(clientSocket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());


        String filename = data.readUTF();
        String fichierName = data.readUTF();
        String fichierVideo = data.readUTF();

        JLabel label = new JLabel();
        label.setText("Que voulez vous lire?");

        JButton button1 = new JButton();
        button1.setText(filename);
        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                try {
                    // ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject(e.getActionCommand());
                    oos.flush();
                    System.out.println("You clicked "+e.getActionCommand());
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        });  
        

        JButton button2 = new JButton();
        button2.setText(fichierName);
        button2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                try {
                    // ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject(e.getActionCommand());
                    oos.flush();
                    System.out.println("You clicked "+e.getActionCommand());
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        }); 

        JButton button3 = new JButton();
        button3.setText(fichierVideo);
        button3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                try {
                    // ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject(e.getActionCommand());
                    System.out.println("You clicked "+e.getActionCommand());
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        }); 


        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(500,500);
        frame.add(label);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        String envoye = (String) ois.readObject();

        if(envoye.contains(".jpg")) {
            System.out.println("Ny sary tsara be hazonao "+envoye);
            /* Affichage Image */
            InputStream inputStream = clientSocket.getInputStream();
            System.out.println("Reading "+System.currentTimeMillis());
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
            jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

        if(envoye.contains(".mp3")) {
            System.out.println("Ny hira tsara be hazonao "+envoye);
            
            /* Envoyer photos */
            int len = 1000000;
            byte[] mybytearray = new byte[len];

            JFrame frame1 = new JFrame();
            frame1.setLayout(new FlowLayout());
            frame1.setSize(500,500);
            JLabel label1 = new JLabel();
            label1.setText(fichierName);
            frame1.add(label1);
            frame1.setVisible(true);
            frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            while(true) {
                data.read(mybytearray, 0, len);
                System.out.println("en cours de lecture...");
                Thread play = new Thread(new PlayMP3(mybytearray));
                play.start();
                play(mybytearray);
            }
        }

        if(envoye.contains(".mp4")) {
            System.out.println("Ny video tsara be hazonao "+envoye);
            /* Envoyer vidéo */
            try {
                
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
                dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                System.out.println("Sending the File to the Server");

                String fileName = "mercredi.mp4";
                int bytes = 0;
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
                long taille = dataInputStream.readLong(); // read file taille
                byte[] buffer = new byte[4 * 1024];
                while (taille > 0 && (bytes = dataInputStream.read( buffer, 0, (int)Math.min(buffer.length, taille))) != -1) {
                    // Here we write the file using write method
                    fileOutputStream.write(buffer, 0, bytes);
                    taille -= bytes; // read upto file taille
                }
                // Here we received file
                System.out.println("File is Received");
                fileOutputStream.close();
                dataInputStream.close();
                dataInputStream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Client(args);
                }
            });
        }

    }

    private Client(String[] args) {
        EmbeddedMediaPlayerComponent component = new EmbeddedMediaPlayerComponent();

        JFrame frame = new JFrame("Video kely by Mialivola");
        frame.setContentPane(component);
        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setVisible(true);

        Canvas c = new Canvas();
        c.setBackground(Color.black);
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(c,BorderLayout.CENTER);
        frame.add(p,BorderLayout.CENTER);
        component.mediaPlayer().media().play("mercredi.mp4");

    }

    public static void play(byte[] data) throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        Player player = new Player(in);
        player.play();
    }
}
