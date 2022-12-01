package serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

    public void getServeur() throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        Socket socket = null;
        ServerSocket serverSocket = new ServerSocket(9000);
        socket = serverSocket.accept();

        File FILE_TO_SEND = new File("C:\\Users\\miali\\Music\\Sariaka\\ckay.mp3");
        byte[] mybytearray = new byte[(int) FILE_TO_SEND.length()];
        try {
            fis = new FileInputStream(FILE_TO_SEND);
            bis = new BufferedInputStream(fis);

        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

        OutputStream os = null;
        bis.read(mybytearray, 0, mybytearray.length);
        os = socket.getOutputStream();
        System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
    }
}
