package project.FMS.dao;

import project.FMS.example.Inventory;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryService {
    public static List<Inventory> getList(Integer page, Integer perPage) throws Throwable {
        DAO dao = new DAO();
        page = (page -1)*perPage;
        List result = dao.select("SELECT * FROM `FruitInventory` LIMIT "+page+", "+perPage);
        List<Inventory> list = new ArrayList<>();
        result.forEach((Object item)->{
            list.add(new Inventory((Map)item));
        });
        return list;
    }

    public static Inventory getById(Integer id) throws Throwable {
        DAO dao = new DAO();
        List result = dao.select("SELECT * FROM `FruitInventory` WHERE `FruitNumber` = "+ id);
        if (result.size() == 0){
            return null;
        }
        return new Inventory((Map)result.get(0));
    }

    public static Boolean addByExample(Inventory inventory) throws Throwable {
        DAO dao = new DAO();
        PreparedStatement set = dao.preSet("INSERT INTO `fruitinventory` (`BatchNumber`, `FruitNumber`, `BatchDinout`, `BatchType`, `BatchQuantity`, `BatchPrice`, `BatchSupplier`, `BatchUnit`) VALUES (NULL, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)");
        set.setInt(1, inventory.getFruitNumber());
        set.setBoolean(2,inventory.getBatchType());
        set.setFloat(3,inventory.getBatchQuantity());
        set.setFloat(4,inventory.getBatchPrice());
        set.setString(5,inventory.getBatchSupplier());
        set.setString(6,inventory.getBatchUnit());
        return dao.runSet(set) != 0;
    }

    public static Boolean updateByExample(Inventory inventory) throws Throwable {
        DAO dao = new DAO();
        PreparedStatement set = dao.preSet("UPDATE `fruitinventory` SET `FruitNumber` = ?, `BatchDinout` = ?, `BatchType` = ?, `BatchQuantity` = ?, `BatchPrice` = ?, `BatchSupplier` = ?, `BatchUnit` = ? WHERE `fruitinventory`.`BatchNumber` = ?");
        set.setInt(1, inventory.getFruitNumber());
        set.setTimestamp(2,inventory.getBatchDinout());
        set.setBoolean(3, inventory.getBatchType());
        set.setFloat(4,inventory.getBatchQuantity());
        set.setFloat(5,inventory.getBatchPrice());
        set.setString(6,inventory.getBatchSupplier());
        set.setString(7,inventory.getBatchUnit());
        set.setInt(8,inventory.getBatchNumber());
        return dao.runSet(set) != 0;
    }

    public static Boolean deleteById(Integer id) throws Throwable {
        DAO dao = new DAO();
        Integer result = dao.operate("DELETE FROM `fruitinventory` WHERE `fruitinventory`.`BatchNumber` = "+ id);
        return result != 0;
    }

    public static Long countList() throws Throwable{
        DAO dao = new DAO();
        Map result = (Map) dao.select("SELECT COUNT(*) `count` FROM `fruitinventory`").get(0);
        return (Long) result.get("count");
    }
}
