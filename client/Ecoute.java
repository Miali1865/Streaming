package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecoute implements ActionListener {

    public Ecoute() {}

    public void actionPerformed (ActionEvent e) {
        String zavatra = e.getActionCommand();
        System.out.println("Te hiaino na hijery "+zavatra+" hono izy ");
    }
    
}
