package main;

import client.*;

public class Main {
    
    public static void main(String[] args) {
        try {
            Client client = new Client();
            //client.getClient();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
