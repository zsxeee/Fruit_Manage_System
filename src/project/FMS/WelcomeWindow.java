package project.FMS;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import project.FMS.window.impl.InfoWindow;
import project.FMS.window.impl.InventoryWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindow extends JFrame implements ActionListener {
    private JButton infoBtn;
    private JButton inventoryBtn;

    public WelcomeWindow(){
        ImageIcon icon=new ImageIcon("resource/background.png");
        JLabel label=new JLabel(icon);
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);
        JPanel btnPanel = new JPanel();
        infoBtn = new JButton("水果信息管理");
        infoBtn.addActionListener(this);
        inventoryBtn = new JButton("库存信息管理");
        inventoryBtn.addActionListener(this);
        Font font = new Font("黑体",Font.PLAIN,30);
        infoBtn.setFont(font);
        inventoryBtn.setFont(font);
        btnPanel.add(infoBtn);
        btnPanel.add(Box.createHorizontalStrut(50));
        btnPanel.add(inventoryBtn);
        btnPanel.setBorder(new EmptyBorder(0,0,50,0));

        btnPanel.setOpaque(false);

        this.add(btnPanel,BorderLayout.SOUTH);
        this.setSize(icon.getIconWidth(),icon.getIconHeight());
        this.setVisible(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        this.setLocation(dimension.width/2 - this.getWidth()/2,dimension.height/2 - this.getHeight()/2);
        this.setTitle("水果库存管理系统");
        this.setIconImage(toolkit.createImage("resource/icon.png"));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.validate();
    }

    public static void main(String[] args){
        try
        {
            BeautyEyeLNFHelper.frameBorderStyle =BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        SwingUtilities.invokeLater(WelcomeWindow::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.infoBtn){
            JFrame frame = new InfoWindow();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.dispose();
        }
        if (e.getSource() == this.inventoryBtn){
            JFrame frame = new InventoryWindow();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.dispose();
        }
    }
}
