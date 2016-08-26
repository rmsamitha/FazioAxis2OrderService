package org.wso2.models;

import org.wso2.FazioService;

/**
Model class related to order with all the details
 */
public class DetailedOrder extends Order {
    private String cutstomerId;
    private String shippingAddress;
    public ItemLine[] itemLines;

    public DetailedOrder(int orderId, String cutstomerId, String shippingAddress, String orderHeader,
            ItemLine[] itemLines) {
        if (isItemIDsValid(itemLines)) {
            this.orderId = orderId;
            this.orderHeader = orderHeader;
            this.cutstomerId = cutstomerId;
            this.shippingAddress = shippingAddress;
            this.itemLines = itemLines;
        } else {
            throw new RuntimeException("Item ID invalid. itemId should be one of IT001,IT002,IT003 or IT004. ");
        }
    }

    /**
     * Check if the item id s of the newly creating orders, are similar to any of the predefined item id.
     *
     * @param itemLines
     * @return
     */
    private static boolean isItemIDsValid(ItemLine[] itemLines) {
        for (int i = 0; i < itemLines.length; i++) { //for each item line
            String itemId = itemLines[i].getItemId();

            if (!FazioService.items.containsKey(itemId)) {
                return false;
            }
        }
        return true;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCutstomerId() {
        return cutstomerId;
    }

    public void setCutstomerId(String cutstomerId) {
        this.cutstomerId = cutstomerId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ItemLine[] getItemLines() {
        return itemLines;
    }

    public void setItemLines(ItemLine[] itemLines) {
        this.itemLines = itemLines;
    }

}
