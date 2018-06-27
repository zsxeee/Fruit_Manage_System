package project.FMS.dao;

import org.junit.Test;
import project.FMS.example.Inventory;

import java.util.List;

import static org.junit.Assert.*;

public class DAOTest {
    @Test
    public void testDao() throws Throwable {
        Inventory inventory = new Inventory();
        inventory.setFruitNumber(1);
        inventory.setBatchType(true);
        inventory.setBatchQuantity(2.1f);
        inventory.setBatchPrice(1.86f);
        inventory.setBatchSupplier("XXX");
        inventory.setBatchUnit("kg");

        boolean a = InventoryService.addByExample(inventory);
        assertEquals(true,a);
    }
}