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

    // DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    // OutputStream outputStream=socket.getOutputStream();
    // File file = new File("C:/Users/miali/Pictures/Camera Roll/cute.jpg");
    // BufferedImage image=ImageIO.read(file);
    // ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    // ImageIO.write(image, "jpg",byteArrayOutputStream );
    // byte[]size =ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

    // outputStream.write(size);
    // outputStream.write(byteArrayOutputStream.toByteArray());
    // out.writeUTF(file.getName().toLowerCase());
    // outputStream.flush();
    // System.out.println("Sending image......");
    // System.out.println("Flushed "+System.currentTimeMillis());

    // Thread.sleep(120000);
    // System.out.println("Closing "+System.currentTimeMillis());
    // socket.close();


    // File file = new File("see you again.mp3");
    // FileInputStream inputStream = new FileInputStream(file);
    // byte[] mybytearray = inputStream.readAllBytes();

    
    // while (true) {
    //   Socket sock = servsock.accept();
    //   System.out.println(sock.getInetAddress());
    //   DataOutputStream out = new DataOutputStream(sock.getOutputStream());
    //   out.writeUTF(file.getName().toLowerCase());
    //   out.write(mybytearray);
    // }

    try (servsock) {
      System.out.println("Connected");
      dataInputStream = new DataInputStream(socket.getInputStream());
      dataOutputStream = new DataOutputStream(socket.getOutputStream());


      String path = "C:/Users/miali/Videos/MERCREDI/mercredi.mp4";
      int bytes = 0;
      File fichier = new File(path);
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
      socket.close();
  }
  catch (Exception e) {
      e.printStackTrace();
  }

  }
    
}

