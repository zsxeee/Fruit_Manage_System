package project.FMS.window;


import project.FMS.dao.InfoService;
import project.FMS.dao.InventoryService;
import project.FMS.example.Info;
import project.FMS.example.Inventory;
import project.FMS.util.Iterables;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class InventoryWindow extends TableWindow{
    @Override
    String[] getColumnNames() {
        return new String[]{"批次编号", "水果", "批次时间", "批次类型", "批次水果数量", "批次单价", "批次供应商", "批次计价单位", "批次总价"};
    }

    @Override
    String getWindowTitle() {
        return "库存管理";
    }

    @Override
    String[][] getList(){
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
            JOptionPane.showMessageDialog(this,"数据获取失败，错误详情：\n" + throwable.toString());
            return null;
        }
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
            JOptionPane.showMessageDialog(this,"数据获取失败，错误详情：\n" + throwable.toString());
            return null;
        }
    }
}
