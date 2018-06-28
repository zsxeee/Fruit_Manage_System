package project.FMS.window;

import project.FMS.dao.InfoService;
import project.FMS.example.Info;
import project.FMS.util.Iterables;

import javax.swing.*;
import java.util.List;

public class InfoWindow extends TableWindow{
    @Override
    String[] getColumnNames() {
        return new String[]{"水果编号", "水果名称", "水果产地"};
    }

    @Override
    String getWindowTitle() {
        return "水果信息管理";
    }

    @Override
    String[][] getList(){
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
            JOptionPane.showMessageDialog(this,"数据获取失败，错误详情：\n" + throwable.toString());
            return null;
        }
    }

}
