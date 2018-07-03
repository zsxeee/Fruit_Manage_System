package project.FMS.window.impl;

import project.FMS.dao.InfoService;
import project.FMS.example.Info;
import project.FMS.util.Iterables;
import project.FMS.window.TableWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InfoWindow extends TableWindow {
    public InfoWindow() {
        super();
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"水果编号", "水果名称", "水果产地"};
    }

    @Override
    protected String getWindowTitle() {
        return "水果信息管理";
    }

    @Override
    protected String[][] getList(){
        try {
            List<Info> list = InfoService.getList();
            String[][] dataList = new String[list.size()][];
            Iterables.forEach(list,(index, item)->{
                String[] temp = new String[]{
                        item.getFruitNumber().toString(),
                        item.getFruitName(),
                        item.getFruitProduction()

                };
                dataList[index] = temp;
            });
            return dataList;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据获取失败，错误详情：\n" + throwable.toString(), "错误", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    protected Boolean delete(String[] row) {
        Integer id = Integer.parseInt(row[0]);
        try {
            if (!InfoService.deleteById(id)){
                JOptionPane.showMessageDialog(this,"删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected Object[] getDialogField() {
        //title
        JLabel title = new JLabel("编辑条目");
        title.setFont(new Font("SimHei", Font.BOLD, 15));
        //name
        JLabel nameLabel = new JLabel("水果名称：");

        JTextField nameField = new JTextField();

        //production
        JLabel productionLabel = new JLabel("水果产地：");
        JTextField productionField = new JTextField();

        return new Object[]{
                title,
                Box.createVerticalStrut(8),
                nameLabel,
                nameField,
                productionLabel,
                productionField,
                Box.createVerticalStrut(20)
        };
    }

    @Override
    protected void setDialogData(Object[] field, String[] row) {
        ((JTextField)field[3]).setText(row[1]);
        ((JTextField)field[5]).setText(row[2]);
    }

    @Override
    protected Boolean save(Integer id, Object[] dialogObjects) throws Throwable {
        Info info = new Info();
        info.setFruitNumber(id);
        info.setFruitName(((JTextField)dialogObjects[3]).getText());
        info.setFruitProduction(((JTextField)dialogObjects[5]).getText());

        return InfoService.updateByExample(info);
    }

    @Override
    protected Boolean add(Object[] dialogObjects) throws Throwable {
        Info info = new Info();
        info.setFruitName(((JTextField)dialogObjects[3]).getText());
        info.setFruitProduction(((JTextField)dialogObjects[5]).getText());

        return InfoService.addByExample(info);
    }
}
