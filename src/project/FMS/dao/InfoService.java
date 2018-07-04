package project.FMS.dao;

import project.FMS.example.Info;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InfoService {
    public static List<Info> getList(Integer page, Integer perPage) throws Throwable {
        DAO dao = new DAO();
        page = (page -1)*perPage;
        List result = dao.select("SELECT`fruitinfo`.*,SUM(IF(`fruitinventory`.`BatchType`,`fruitinventory`.`BatchQuantity`, - `fruitinventory`.`BatchQuantity`)) `total` FROM `fruitinfo` LEFT JOIN `fruitinventory` ON `fruitinfo`.`FruitNumber` = `fruitinventory`.`FruitNumber` GROUP BY `fruitinfo`.`FruitNumber` LIMIT "+page+", "+perPage);
        List<Info> list = new ArrayList<>();
        result.forEach((Object item)->{
            list.add(new Info((Map)item));
        });
        return list;
    }

    public static Info getById(Integer id) throws Throwable {
        DAO dao = new DAO();
        List result = dao.select("SELECT * FROM `FruitInfo` WHERE `FruitNumber` = "+ id);
        if (result.size() == 0){
            return null;
        }
        return new Info((Map)result.get(0));
    }

    public static Boolean addByExample(Info info) throws Throwable {
        DAO dao = new DAO();
        PreparedStatement set = dao.preSet("INSERT INTO `fruitinfo` (`FruitNumber`, `FruitName`, `FruitProduction`) VALUES (NULL, ?, ?)");
        set.setString(1, info.getFruitName());
        set.setString(2,info.getFruitProduction());
        return dao.runSet(set) != 0;
    }

    public static Boolean updateByExample(Info info) throws Throwable {
        DAO dao = new DAO();
        PreparedStatement set = dao.preSet("UPDATE `fruitinfo` SET `FruitName` = ?, `FruitProduction` = ? WHERE `fruitinfo`.`FruitNumber` = ?");
        set.setString(1, info.getFruitName());
        set.setString(2,info.getFruitProduction());
        set.setInt(3, info.getFruitNumber());
        return dao.runSet(set) != 0;
    }

    public static Boolean deleteById(Integer id) throws Throwable {
        DAO dao = new DAO();
        Integer result = dao.operate("DELETE FROM `fruitinfo` WHERE `fruitinfo`.`FruitNumber` = "+ id);
        return result != 0;
    }

    public static Long countList() throws Throwable{
        DAO dao = new DAO();
        Map result = (Map) dao.select("SELECT COUNT(*) `count` FROM `fruitinfo`").get(0);
        return (Long) result.get("count");
    }
}
