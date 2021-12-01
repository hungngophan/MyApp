package com.example.myapp_nguyenviethung;

public class Product {
     String dt;
     String temp_min;
     String tem_max;
     String description;
     String icon;

    public Product(String dt, String temp_min, String tem_max, String description, String icon) {
        this.dt = dt;
        this.temp_min = temp_min;
        this.tem_max = tem_max;
        this.description = description;
        this.icon = icon;
    }

    public Product(String dt, String temp_min, String tem_max, String icon) {
        this.dt = dt;
        this.temp_min = temp_min;
        this.tem_max = tem_max;
        this.icon = icon;
    }


    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTem_max() {
        return tem_max;
    }

    public void setTem_max(String tem_max) {
        this.tem_max = tem_max;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
