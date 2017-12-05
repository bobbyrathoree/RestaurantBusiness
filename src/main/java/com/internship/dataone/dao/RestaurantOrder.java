package com.internship.dataone.dao;

public class RestaurantOrder {

    private int id;
    private String customer_name;
    private String item_ordered;

    public RestaurantOrder(int id, String customer_name, String item_ordered) {
        super();
        this.id = id;
        this.customer_name = customer_name;
        this.item_ordered = item_ordered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getItem_ordered() {
        return item_ordered;
    }

    public void setItem_ordered(String item_ordered) {
        this.item_ordered = item_ordered;
    }

    public RestaurantOrder() {
    }
}
