package com.example.smartphonestore.model;

public class Cart {
    private int id;
    private double totalCost;
    private int userId;

    public Cart() {
    }

    public Cart(int userId) {
        this.userId = userId;
    }

    public Cart(double totalCost, int userId) {
        this.totalCost = totalCost;
        this.userId = userId;
    }

    public Cart(int id, double totalCost, int userId) {
        this.id = id;
        this.totalCost = totalCost;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalCost=" + totalCost +
                ", userId=" + userId +
                '}';
    }
}
