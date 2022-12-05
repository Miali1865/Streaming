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

    Socket socket=null;
    socket = servsock.accept();
    OutputStream outputStream=socket.getOutputStream();
    BufferedImage image=ImageIO.read(new File("C:/Users/miali/Pictures/Camera Roll/cute.jpg"));
    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    ImageIO.write(image, "jpg",byteArrayOutputStream );
    byte[]size =ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
    outputStream.write(size);
    outputStream.write(byteArrayOutputStream.toByteArray());
    outputStream.flush();
    System.out.println("Sending image......");
    System.out.println("Flushed"+System.currentTimeMillis());

    Thread.sleep(120000);
    System.out.println("Closing"+System.currentTimeMillis());
    socket.close();



  }
    
}

