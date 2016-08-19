package org.wso2;

/**
 * Created by samithac on 19/8/16.
 */
public class OpenOrder {
    int orderId;
    String orderHeader;

    public OpenOrder(int orderId, String orderHeader) {
        this.orderId = orderId;
        this.orderHeader = orderHeader;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(String orderHeader) {
        this.orderHeader = orderHeader;
    }
}
