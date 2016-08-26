package org.wso2.models;

/**
Model class related to order with less details
 */
public class OrderInBrief extends Order {
    public OrderInBrief(int orderId, String orderHeader) {
        super.orderId = orderId;
        super.orderHeader = orderHeader;
    }
}
