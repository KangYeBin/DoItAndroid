package org.techtown.doitmission14;

public class Product {
    String name;
    String cost;
    String detail;

    public Product(String name, String cost, String detail) {
        this.name = name;
        this.cost = cost;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
