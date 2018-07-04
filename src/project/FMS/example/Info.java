package project.FMS.example;

import java.util.Map;

public class Info {
    public Info(Map map){
        this.FruitNumber = (Integer) map.get("FruitNumber");
        this.FruitName = (String) map.get("FruitName");
        this.FruitProduction = (String) map.get("FruitProduction");
        this.total = (Double) map.get("total");
    }

    public Info(){}

    private Integer FruitNumber;
    private String FruitName;
    private String FruitProduction;
    private Double total;

    public Integer getFruitNumber() {
        return FruitNumber;
    }

    public void setFruitNumber(Integer fruitNumber) {
        FruitNumber = fruitNumber;
    }

    public String getFruitName() {
        return FruitName;
    }

    public void setFruitName(String fruitName) {
        FruitName = fruitName;
    }

    public String getFruitProduction() {
        return FruitProduction;
    }

    public void setFruitProduction(String fruitProduction) {
        FruitProduction = fruitProduction;
    }

    public Double getTotal() {
        return total;
    }
}