package project.FMS.info;

import javax.swing.*;

public class InfoWindow extends JFrame {
    public InfoWindow(){
        setBounds(100,100,800,600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        validate();
    }

    public static void main(String[] args){
        new InfoWindow();
    }
}
