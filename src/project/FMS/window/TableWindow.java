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
    private JMenuItem switchMenuItem, exitMenuItem, addMenuItem, refreshMenuItem;
    private JTable table;
    private DefaultTableModel model;
    private JPopupMenu popupMenu;
    protected Integer currPageCount = 1;
    private Long total = this.getTotalCount();
    protected Integer per = 30;
    private Integer page = Math.toIntExact(this.total / this.per)+1;

    public void setCurrPageCount(Integer currPageCount) {
        this.currPageCount = currPageCount;
        this.pageSelect.setSelectedItem(currPageCount);

        this.model.setDataVector(getList(),getColumnNames());
        this.model.fireTableDataChanged();
    }

    private JComboBox<Integer> pageSelect;

    //method
    protected abstract String[][] getList();
    protected abstract String[] getColumnNames();
    protected abstract String getWindowTitle();
    protected abstract Long getTotalCount();

    protected TableWindow(){
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
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        table = new JTable(this.model);
        table.setFont(new Font("宋体",Font.BOLD,20));
        table.setRowHeight(30);
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, right);
        JScrollPane scrollPane = new JScrollPane(table);
        basePanel.add(scrollPane, BorderLayout.CENTER);
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
        JLabel totalCount = new JLabel("总记录数："+ total.toString());
        JLabel currCount = new JLabel("当前显示记录数："+ this.model.getRowCount());
        JLabel perCount = new JLabel("每页显示记录数："+ per);
        JButton prevPage = new JButton("上一页");
        prevPage.addActionListener(evt->{
            if(currPageCount != 1){
                this.setCurrPageCount(currPageCount -1);
            }
        });
        Integer[] pageArray = new Integer[page];
        for (Integer i=page;i>0;i--){
            pageArray[i-1] = i;
        }
        this.pageSelect = new JComboBox<>(pageArray);
        this.pageSelect.setSelectedItem(currPageCount);
        this.pageSelect.addActionListener(evt->{
            this.setCurrPageCount((Integer) pageSelect.getSelectedItem());
        });
        JButton nextPage = new JButton("下一页");
        nextPage.addActionListener(evt->{
            if(!currPageCount.equals(page)){
                this.setCurrPageCount(currPageCount +1);
            }
        });

        btnPanel.add(totalCount);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(currCount);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(perCount);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(prevPage);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(pageSelect);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(nextPage);

        //add
        basePanel.add(btnPanel,BorderLayout.SOUTH);
        this.add(basePanel,BorderLayout.CENTER);

        this.setBounds(100,100,1000,800);

        this.setTitle(getWindowTitle());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
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
            Object[] dialogObjects = getDialogField();

            int result = JOptionPane.showConfirmDialog(
                    this,
                    dialogObjects,
                    "新增条目",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION){
                try {
                    if(!add(dialogObjects)){
                        JOptionPane.showMessageDialog(this, "保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Throwable throwable) {
                    JOptionPane.showMessageDialog(this, "保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    throwable.printStackTrace();
                }finally {
                    model.setDataVector(getList(),getColumnNames());
                    model.fireTableDataChanged();
                }
            }
        }
        else if (e.getSource() == this.refreshMenuItem){
            model.setDataVector(this.getList(),getColumnNames());
            model.fireTableDataChanged();
        }
    }

    private void createPopupMenu() {
        Component frame = this;
        popupMenu = new JPopupMenu();

        JMenuItem editMenItem = new JMenuItem();
        editMenItem.setText("  编辑  ");
        Icon editIcon = IconFontSwing.buildIcon(FontAwesome.PENCIL, 16, Color.BLACK);
        editMenItem.setIcon(editIcon);
        editMenItem.addActionListener(evt -> {
            Integer rowIndex = table.getSelectedRow();
            String [] row = getList()[rowIndex];

            Object[] dialogObjects = getDialogField();
            setDialogData(dialogObjects, row);

            int result = JOptionPane.showConfirmDialog(
                frame,
                    dialogObjects,
                "编辑条目",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION){
                try {
                    if(!save(Integer.parseInt(row[0]), dialogObjects)){
                        JOptionPane.showMessageDialog(frame, "保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Throwable throwable) {
                    JOptionPane.showMessageDialog(frame, "保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    throwable.printStackTrace();
                }finally {
                    model.setDataVector(getList(),getColumnNames());
                    model.fireTableDataChanged();
                }
            }
        });
        JMenuItem delMenItem = new JMenuItem();
        delMenItem.setText("  删除  ");
        Icon delIcon = IconFontSwing.buildIcon(FontAwesome.TRASH, 16, Color.BLACK);
        delMenItem.setIcon(delIcon);
        delMenItem.addActionListener(evt -> {
            if(JOptionPane.showConfirmDialog(frame,"确认删除此行？","警告", JOptionPane.YES_NO_OPTION)==0){
                Integer rowIndex = table.getSelectedRow();
                String [] row = getList()[rowIndex];
                if (delete(row)){
                    model.setDataVector(getList(),getColumnNames());
                    model.fireTableDataChanged();
                }else {
                    JOptionPane.showMessageDialog(frame, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        popupMenu.add(editMenItem);
        popupMenu.add(delMenItem);
    }

    protected abstract Boolean delete(String[] row);
    protected abstract Object[] getDialogField();
    protected abstract void setDialogData(Object[] field, String[] row);

    protected abstract Boolean save(Integer id, Object[] dialogObjects) throws Throwable;
    protected abstract Boolean add(Object[] dialogObjects) throws Throwable;
}

