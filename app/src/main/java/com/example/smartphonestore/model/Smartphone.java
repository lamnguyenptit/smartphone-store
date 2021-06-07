package com.example.smartphonestore.model;

import java.io.Serializable;

public class Smartphone implements Serializable {
    private int id;
    private String name;
    private String brand;
    private String chipset;
    private String ram;
    private double price;
    private String image;

    public Smartphone() {
    }

    public Smartphone(int id, String name, String brand, String chipset, String ram, double price, String image) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.chipset = chipset;
        this.ram = ram;
        this.price = price;
        this.image = image;
    }

    public Smartphone(String name, String brand, String chipset, String ram, double price, String image) {
        this.name = name;
        this.brand = brand;
        this.chipset = chipset;
        this.ram = ram;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "id:" + id +
                "\nname: " + name +
                "\nbrand: " + brand +
                "\nchipset: " + chipset +
                "\nram: " + ram +
                "\nprice: " + price;
    }
}
