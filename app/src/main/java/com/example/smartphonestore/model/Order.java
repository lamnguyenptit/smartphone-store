package com.example.smartphonestore.model;

public class Order {
    private int id;
    private double totalCost;
    private String date;
    private boolean isDone;
    private int userId;

    public Order() {
    }

    public Order(double totalCost, String date, boolean isDone, int userId) {
        this.totalCost = totalCost;
        this.date = date;
        this.isDone = isDone;
        this.userId = userId;
    }

    public Order(int id, double totalCost, String date, boolean isDone, int userId) {
        this.id = id;
        this.totalCost = totalCost;
        this.date = date;
        this.isDone = isDone;
        this.userId = userId;
    }

    public Order(double totalCost, String date, int userId) {
        this.totalCost = totalCost;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "\ndate: " + date +
                "\ntotalCost: " + totalCost;
    }
}
