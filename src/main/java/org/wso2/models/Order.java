package org.wso2.models;

/**
 * Model class related to order with all the details
 */
public class Order {
    public int orderId;
    public String orderHeader;

    public Order() {
    }

    public Order(int orderId, String orderHeader) {
        this.orderId = orderId;
        this.orderHeader = orderHeader;
    }

    public String getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(String orderHeader) {
        this.orderHeader = orderHeader;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
