package project.FMS.welcome;

import javax.swing.*;

public class WelcomeWindow extends JFrame {
    public WelcomeWindow(){
        setBounds(100,100,800,600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        validate();
    }

    public static void main(String[] args){
        new WelcomeWindow();
    }
}
