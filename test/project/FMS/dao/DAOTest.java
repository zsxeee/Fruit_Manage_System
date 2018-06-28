package project.FMS.dao;

import org.junit.Test;
import project.FMS.example.Inventory;

import java.util.List;

import static org.junit.Assert.*;

public class DAOTest {
    @Test
    public void testDao() throws Throwable {
        Inventory inventory = new Inventory();
        inventory.setFruitNumber(3);
        inventory.setBatchType(false);
        inventory.setBatchQuantity(500f);
        inventory.setBatchPrice(3.21f);
        inventory.setBatchSupplier("测试");
        inventory.setBatchUnit("kg");

        boolean a = InventoryService.addByExample(inventory);
        assertEquals(true,a);
    }
}