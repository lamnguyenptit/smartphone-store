package com.example.smartphonestore.model;

public class Item {
    private int id;
    private int quantity;
    private int smartphoneId;
    private int orderId;
    private int cartId;

    public Item() {
    }

    public Item(int quantity, int smartphoneId, int cartId) {
        this.quantity = quantity;
        this.smartphoneId = smartphoneId;
        this.cartId = cartId;
    }

    public Item(int quantity, int smartphoneId, int orderId, int cartId) {
        this.quantity = quantity;
        this.smartphoneId = smartphoneId;
        this.orderId = orderId;
        this.cartId = cartId;
    }
    public Item(int id, int quantity, int smartphoneId, int orderId, int cartId) {
        this.id = id;
        this.quantity = quantity;
        this.smartphoneId = smartphoneId;
        this.orderId = orderId;
        this.cartId = cartId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSmartphoneId() {
        return smartphoneId;
    }

    public void setSmartphoneId(int smartphoneId) {
        this.smartphoneId = smartphoneId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", smartphoneId=" + smartphoneId +
                ", orderId=" + orderId +
                ", cartId=" + cartId +
                '}';
    }
}
