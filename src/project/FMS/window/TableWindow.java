package project.FMS.window;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;
import project.FMS.WelcomeWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public abstract class TableWindow extends JFrame implements ActionListener{
    JPanel basePanel;
    JMenuItem switchMenuItem, exitMenuItem, addMenuItem, refreshMenuItem;
    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;
    JPopupMenu popupMenu;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    //method
    abstract String[][] getList();
    abstract String[] getColumnNames();
    abstract String getWindowTitle();

    TableWindow(){
        IconFontSwing.register(FontAwesome.getIconFont());
        //setMenu
        JMenuBar menuBar = new JMenuBar();
        JMenu sysMenu = new JMenu("系统");
        switchMenuItem = new JMenuItem("切换模式");
        Icon switchIcon = IconFontSwing.buildIcon(FontAwesome.EXCHANGE, 16, Color.BLACK);
        switchMenuItem.setIcon(switchIcon);
        switchMenuItem.addActionListener(this);
        exitMenuItem=new JMenuItem("退出");
        Icon exitIcon = IconFontSwing.buildIcon(FontAwesome.SIGN_OUT, 16, Color.BLACK);
        exitMenuItem.setIcon(exitIcon);
        exitMenuItem.addActionListener(this);
        sysMenu.add(switchMenuItem);
        sysMenu.add(exitMenuItem);

        JMenu itemMenu = new JMenu("条目");
        addMenuItem = new JMenuItem("新增条目");
        Icon addIcon = IconFontSwing.buildIcon(FontAwesome.PLUS, 16, Color.BLACK);
        addMenuItem.setIcon(addIcon);
        addMenuItem.addActionListener(this);
        refreshMenuItem = new JMenuItem("刷新列表");
        Icon refreshIcon = IconFontSwing.buildIcon(FontAwesome.REFRESH, 16, Color.BLACK);
        refreshMenuItem.setIcon(refreshIcon);
        refreshMenuItem.addActionListener(this);
        itemMenu.add(addMenuItem);
        itemMenu.add(refreshMenuItem);

        menuBar.add(sysMenu);
        menuBar.add(itemMenu);
        this.setJMenuBar(menuBar);

        //setBase
        this.model = new DefaultTableModel(this.getList(), getColumnNames()) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        this.basePanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        table = new JTable(this.model);
        table.setFont(new Font("宋体",Font.BOLD,20));
        table.setRowHeight(30);
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, right);
        this.scrollPane = new JScrollPane(table);
        this.basePanel.add(this.scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(table);

        //setPopMenu
        this.createPopupMenu();
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    int focusedRowIndex = table.rowAtPoint(evt.getPoint());
                    if (focusedRowIndex == -1) {
                        return;
                    }
                    table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                    popupMenu.show(table, evt.getX(), evt.getY());
                }
            }
        });

        //setBtn
        JPanel btnPanel = new JPanel();
        JLabel currCount = new JLabel("当前显示记录数："+this.model.getRowCount());
        btnPanel.add(currCount);
        //add
        basePanel.add(btnPanel,BorderLayout.SOUTH);
        this.add(this.basePanel,BorderLayout.CENTER);

        this.setBounds(100,100,1000,800);

        this.setTitle(getWindowTitle());
        this.setIconImage(toolkit.createImage("resource/icon.png"));
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i=JOptionPane.showConfirmDialog(null, "确定要退出系统吗？", "退出系统", JOptionPane.YES_NO_OPTION);
                if(i==JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        this.setVisible(true);
        this.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.switchMenuItem){
            new WelcomeWindow();
            this.dispose();
        }
        else if (e.getSource() == this.exitMenuItem){
            if(JOptionPane.showConfirmDialog(this,"确认退出系统？","退出系统", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
        else if (e.getSource() == this.addMenuItem){
            new JOptionPane();
        }
        else if (e.getSource() == this.refreshMenuItem){
            model.setDataVector(this.getList(),getColumnNames());
            model.fireTableDataChanged();
        }
    }

    void createPopupMenu() {
        Component frame = this;
        popupMenu = new JPopupMenu();

        JMenuItem editMenItem = new JMenuItem();
        editMenItem.setText("  编辑  ");
        Icon editIcon = IconFontSwing.buildIcon(FontAwesome.PENCIL, 16, Color.BLACK);
        editMenItem.setIcon(editIcon);
        editMenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //该操作需要做的事
            }
        });
        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("  删除  ");
        Icon delIcon = IconFontSwing.buildIcon(FontAwesome.TRASH, 16, Color.BLACK);
        delMenItem.setIcon(delIcon);
        delMenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(JOptionPane.showConfirmDialog(frame,"确认删除此行？","警告", JOptionPane.YES_NO_OPTION)==0){
                    System.out.println(evt);
                }

            }
        });

        popupMenu.add(editMenItem);
        popupMenu.add(delMenItem);
    }
}

