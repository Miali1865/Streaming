package serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import java.lang.*;
import javax.imageio.ImageIO;

public class Serveur {

  private static DataOutputStream dataOutputStream = null;
  private static DataInputStream dataInputStream = null;
  private static Socket s=null;

  public static void main(String[] args) throws IOException, ClassNotFoundException,InterruptedException {

    ServerSocket servsock = new ServerSocket(4000);

    Socket socket = servsock.accept();
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    File file = new File("C:/Users/miali/Pictures/Camera Roll/cute.jpg");
    out.writeUTF(file.getName().toLowerCase());

    File fichierMp3 = new File("see you again.mp3");
    out.writeUTF(fichierMp3.getName().toLowerCase());

    //String path = "C:/Users/miali/Videos/MERCREDI/mercredi.mp4";
    File fichier = new File("C:/Users/miali/Videos/MERCREDI/mercredi.mp4");
    dataOutputStream = new DataOutputStream(socket.getOutputStream());
    dataOutputStream.writeUTF(fichier.getName().toLowerCase());

    while(true) {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      String demande = (String) ois.readObject();

      if(demande.contains(".jpg")) {
        System.out.println("Hahazo sary tsara be ianao !");
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(demande);
        /* Envoyer Image */
        OutputStream outputStream=socket.getOutputStream();
        BufferedImage image=ImageIO.read(file);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ImageIO.write(image, "jpg",byteArrayOutputStream );
        byte[] size =ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Sending image......");
        System.out.println("Flushed "+System.currentTimeMillis());

        Thread.sleep(120000);
        System.out.println("Closing "+System.currentTimeMillis());
        socket.close();
      }

      if(demande.contains(".mp3")) {
        System.out.println("Hahazo hira tsara be ianao !");
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(demande);
        /* Envoyer Musique */
        FileInputStream inputStream = new FileInputStream(fichierMp3);
        byte[] mybytearray = inputStream.readAllBytes();

        
        while (true) {
          System.out.println("Connected");
          System.out.println(socket.getInetAddress());
          out.writeUTF(fichierMp3.getName().toLowerCase());
          out.write(mybytearray);
        }
      }

      if(demande.contains(".mp4")) {
        System.out.println("Hahazo video tsara be ianao !");
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(demande);
        /* Envoyer vidéo */
        try {
          System.out.println("Connected");
          dataInputStream = new DataInputStream(socket.getInputStream());
          dataOutputStream = new DataOutputStream(socket.getOutputStream());

          int bytes = 0;
          FileInputStream fileInputStream = new FileInputStream(fichier);

          dataOutputStream.writeLong(fichier.length());

          byte[] buffer = new byte[4 * 1024];
          while ((bytes = fileInputStream.read(buffer)) != -1) {
          
          dataOutputStream.write(buffer, 0, bytes);
          dataOutputStream.flush();
          }
          fileInputStream.close();
          dataInputStream.close();
          dataOutputStream.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

  }
    
}

