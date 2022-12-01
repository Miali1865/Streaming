package client;

import java.io.*;
import java.net.Socket;

public class Client {
    
    public void getClient() throws IOException {
        Socket clientSocket = null;
        clientSocket = new Socket("localhost",9000);
        File file_to_save = new File("C:\\Users\\miali\\Music\\socket\\good.mp3");
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        byte[] mybytearray = new byte[9429672];
        InputStream is = clientSocket.getInputStream();
        fos = new FileOutputStream(file_to_save);
        bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearray, 0, mybytearray.length);
        current = bytesRead;

        do {
            bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
            if (bytesRead >= 0) {
                current += bytesRead;
                bos.write(mybytearray, 0, current);
                bos.flush();
                System.out.println("File tsew.mp3"+ " downloaded (" + current + " bytes read)");
            }
        } while (bytesRead > -1);

        // bos.write(mybytearray, 0, current);
        // bos.flush();
        // System.out.println("File tsew.mp3"+ " downloaded (" + current + " bytes read)");

    }
}
