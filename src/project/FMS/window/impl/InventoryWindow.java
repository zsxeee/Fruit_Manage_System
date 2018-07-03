package project.FMS.window.impl;


import project.FMS.dao.InfoService;
import project.FMS.dao.InventoryService;
import project.FMS.example.Info;
import project.FMS.example.Inventory;
import project.FMS.util.DateToTimestamp;
import project.FMS.util.Iterables;
import project.FMS.window.TableWindow;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryWindow extends TableWindow {
    @Override
    protected String[] getColumnNames() {
        return new String[]{"批次编号", "水果", "批次时间", "批次类型", "批次水果数量", "批次单价", "批次供应商", "批次计价单位", "批次总价"};
    }

    @Override
    protected String getWindowTitle() {
        return "库存管理";
    }

    @Override
    protected String[][] getList(){
        try {
            List<Inventory> list = InventoryService.getList();
            String[][] dataList = new String[list.size()][];
            Iterables.forEach(list,(index, item)->{
                String[] temp = new String[]{
                        item.getBatchNumber().toString(),
                        this.getFruitName(item.getFruitNumber()),
                        new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(item.getBatchDinout()),
                        item.getBatchType()? "入库":"出库",
                        item.getBatchQuantity().toString(),
                        item.getBatchPrice().toString(),
                        item.getBatchSupplier(),
                        item.getBatchUnit(),
                        String.valueOf(new DecimalFormat("#.00").format(item.getBatchPrice() * item.getBatchQuantity()))
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
            if(!InventoryService.deleteById(id)){
                JOptionPane.showMessageDialog(this,"删除失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected Object[] getDialogField() {

        JLabel title = new JLabel("编辑条目");
        title.setFont(new Font("SimHei", Font.BOLD, 15));

        JLabel nameLabel = new JLabel("水果：");
        JComboBox<String> nameComboBox = new JComboBox<>(Objects.requireNonNull(this.getFruitList()));

        JLabel timeLabel = new JLabel("批次时间：");
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "yyyy年MM月dd日 HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setEnabled(false);
        timeSpinner.setToolTipText("不允许更改批次时间");

        JLabel typeNumLabel = new JLabel("批次类型：");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"入库", "出库"});

        JLabel fruitNumLabel = new JLabel("水果数量：");
        JSpinner fruitNumSpinner = new JSpinner(new SpinnerNumberModel(
                0,
                0,
                9999.99,
                0.1
        ));

        JLabel priceLabel = new JLabel("批次单价：");
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(
                0,
                0,
                99.99,
                0.1
        ));

        JLabel supplierLabel = new JLabel("供应商：");
        JTextField supplierField = new JTextField();

        JLabel unitLabel = new JLabel("计价单位：");
        JComboBox<String> unitComboBox = new JComboBox<>(new String[]{
                "千克", "克", "个", "组"
        });

        return new Object[]{
                title,
                Box.createVerticalStrut(8),
                nameLabel,
                nameComboBox,
                timeLabel,
                timeSpinner,
                typeNumLabel,
                typeComboBox,
                fruitNumLabel,
                fruitNumSpinner,
                priceLabel,
                priceSpinner,
                supplierLabel,
                supplierField,
                unitLabel,
                unitComboBox,
                Box.createVerticalStrut(20)
        };
    }

    @Override
    protected void setDialogData(Object[] field, String[] row) {
        ((JComboBox)field[3]).setSelectedItem(row[0]+"#"+row[1]);
        try {
            ((JSpinner)field[5]).setValue(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").parse(row[2]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((JComboBox)field[7]).setSelectedItem(row[3]);
        ((JSpinner)field[9]).setValue(Float.parseFloat(row[4]));
        ((JSpinner)field[11]).setValue(Float.parseFloat(row[5]));
        ((JTextField)field[13]).setText(row[6]);
        ((JComboBox)field[15]).setSelectedItem(row[7]);
    }

    @Override
    protected Boolean save(Integer id, Object[] dialogObjects) throws Throwable {
        Inventory inventory = new Inventory();
        inventory.setBatchNumber(id);
        Matcher matcher = Pattern.compile("^(\\d+)#").matcher((
                (String)Objects.requireNonNull(((JComboBox) dialogObjects[3]).getSelectedItem()))
        );
        if (matcher.find())
            inventory.setFruitNumber(Integer.parseInt(matcher.group(1)));
        inventory.setBatchDinout(DateToTimestamp.parse((Date) (((JSpinner)dialogObjects[5]).getValue())));
        inventory.setBatchType(
                Objects.equals(((JComboBox) dialogObjects[7])
                        .getSelectedItem(), "入库")
        );
        inventory.setBatchQuantity(Float.parseFloat(
            new DecimalFormat("######0.00").format(
                (((JSpinner)dialogObjects[9]).getValue())
            )
        ));
        inventory.setBatchPrice(Float.parseFloat(
            new DecimalFormat("######0.00").format(
                (((JSpinner)dialogObjects[11]).getValue())
            )
        ));
        inventory.setBatchSupplier(((JTextField)dialogObjects[13]).getText());
        inventory.setBatchUnit((String)(((JComboBox)dialogObjects[15]).getSelectedItem()));

        return InventoryService.updateByExample(inventory);
    }

    @Override
    protected Boolean add(Object[] dialogObjects) throws Throwable {
        Inventory inventory = new Inventory();
        Matcher matcher = Pattern.compile("^(\\d+)#").matcher((
                (String)Objects.requireNonNull(((JComboBox) dialogObjects[3]).getSelectedItem()))
        );
        if (matcher.find())
            inventory.setFruitNumber(Integer.parseInt(matcher.group(1)));
        inventory.setBatchDinout(DateToTimestamp.parse((Date) (((JSpinner)dialogObjects[5]).getValue())));
        inventory.setBatchType(
                Objects.equals(((JComboBox) dialogObjects[7])
                        .getSelectedItem(), "入库")
        );
        inventory.setBatchQuantity(Float.parseFloat(
                new DecimalFormat("######0.00").format(
                        ((JSpinner)dialogObjects[9]).getValue()
                )
        ));
        inventory.setBatchPrice(Float.parseFloat(
                new DecimalFormat("######0.00").format(
                        ((JSpinner)dialogObjects[11]).getValue()
                )
        ));
        inventory.setBatchSupplier(((JTextField)dialogObjects[13]).getText());
        inventory.setBatchUnit((String)(((JComboBox)dialogObjects[15]).getSelectedItem()));

        return InventoryService.addByExample(inventory);
    }

    private String getFruitName(Integer fruitNumber) {
        try {
            Info result = InfoService.getById(fruitNumber);
            if (result==null){
                return "未知ID: "+ fruitNumber;
            }
            return result.getFruitName();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据获取失败，错误详情：\n" + throwable.toString(), "错误", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private String[] getFruitList(){
        try {
            List<Info> list = InfoService.getList();
            String[] dataList = new String[list.size()];
            Iterables.forEach(list,(index, item)-> dataList[index] =item.getFruitNumber() + "#" + item.getFruitName());
            return dataList;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            JOptionPane.showMessageDialog(this,"数据获取失败，错误详情：\n" + throwable.toString(), "错误", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
