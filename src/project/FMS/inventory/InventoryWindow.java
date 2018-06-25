package project.FMS.inventory;

import javax.swing.*;

public class InventoryWindow extends JFrame {
    public InventoryWindow(){
        setBounds(100,100,800,600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        validate();
    }

    public static void main(String[] args){
        new InventoryWindow();
    }
}
