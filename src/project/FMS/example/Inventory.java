package project.FMS.example;

import java.sql.Timestamp;
import java.util.Map;

public class Inventory {
    public Inventory(Map map){
        this.BatchNumber = (Integer) map.get("BatchNumber");
        this.FruitNumber = (Integer) map.get("FruitNumber");
        this.BatchDinout = (Timestamp) map.get("BatchDinout");
        this.BatchType = (Boolean) map.get("BatchType");
        this.BatchQuantity = (Float) map.get("BatchQuantity");
        this.BatchPrice = (Float) map.get("BatchPrice");
        this.BatchSupplier = (String) map.get("BatchSupplier");
        this.BatchUnit = (String) map.get("BatchUnit");
    }

    public Inventory(){}

    private Integer BatchNumber;
    private Integer FruitNumber;
    private Timestamp BatchDinout;
    private Boolean BatchType;
    private Float BatchQuantity;
    private Float BatchPrice;
    private String BatchSupplier;
    private String BatchUnit;

    public Integer getBatchNumber() {
        return BatchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        BatchNumber = batchNumber;
    }

    public Integer getFruitNumber() {
        return FruitNumber;
    }

    public void setFruitNumber(Integer fruitNumber) {
        FruitNumber = fruitNumber;
    }

    public Timestamp getBatchDinout() {
        return BatchDinout;
    }

    public void setBatchDinout(Timestamp BatchDinout) {
        this.BatchDinout = BatchDinout;
    }

    public Boolean getBatchType() {
        return BatchType;
    }

    public void setBatchType(Boolean batchType) {
        BatchType = batchType;
    }

    public Float getBatchQuantity() {
        return BatchQuantity;
    }

    public void setBatchQuantity(Float batchQuantity) {
        BatchQuantity = batchQuantity;
    }

    public Float getBatchPrice() {
        return BatchPrice;
    }

    public void setBatchPrice(Float batchPrice) {
        BatchPrice = batchPrice;
    }

    public String getBatchSupplier() {
        return BatchSupplier;
    }

    public void setBatchSupplier(String batchSupplier) {
        BatchSupplier = batchSupplier;
    }

    public String getBatchUnit() {
        return BatchUnit;
    }

    public void setBatchUnit(String batchUnit) {
        BatchUnit = batchUnit;
    }
}
