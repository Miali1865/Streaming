package main;

import serveur.*;

public class Main {
    
    public static void main(String[] args) {
        try {
            Serveur serveur = new Serveur();
            //serveur.getServeur();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
