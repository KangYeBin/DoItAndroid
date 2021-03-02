package org.techtown.doitmission13;

public class Customer {
    String name;
    String date;
    String phone;

    public Customer(String name, String date, String phone) {
        this.name = name;
        this.date = date;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
