package org.wso2;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.json.JSONObject;
import org.wso2.models.*;

//import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;

/*
SOAP API service class
 */
public class FazioService {
    public static Map<String, Item> items = new HashMap<String, Item>();
    private static Map<Integer, DetailedOrder> orders = new HashMap<Integer, DetailedOrder>();
    static private int orderCount = 1000;

    static {
        items.put("IT001", new Item("IT001", "Mouse", 25.0, "$US"));
        items.put("IT002", new Item("IT002", "Kingston Flsh Drive", 25.0, "$US"));
        items.put("IT003", new Item("IT003", "SD card-10gb", 30.0, "$US"));
        items.put("IT004", new Item("IT004", "Cooling Fan", 40.0, "$US"));
    }

    /**
     * Get all the details of the given order ID
     *
     * @param orderId
     * @return
     */
    public DetailedOrder getOrder(int orderId) {
        DetailedOrder od = orders.get(orderId);
        if (od == null) {
            throw new RuntimeException("No order available with the given orderId");
        }
        return od;
    }

    /**
     * @param customerId
     * @param shippingAddress
     * @param orderHeader
     * @param itemLines
     * @return orderId Auto generated order id incrementing 1 by 1
     */
    public int submitOrder(String customerId, String shippingAddress, String orderHeader, ItemLine[] itemLines) {
        try {

            int orderId = orderCount;
            DetailedOrder order = new DetailedOrder(orderId, customerId, shippingAddress, orderHeader, itemLines);
            orders.put(orderId, order);
            orderCount++;
            return orderId;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "So the order submission failed.");
        }
    }

    /**
     * @param customerId
     * @return
     */
    public Order[] getOpenOrders(String customerId) {
        MessageContext context = MessageContext.getCurrentMessageContext();
        HttpServletRequest req = (HttpServletRequest) context.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
        String jwtHeader = req.getHeader("x-jwt-assertion");
        String applicationTier = null;
        if (jwtHeader != null) {
            String jwtPayload = jwtHeader.split("\\.")[1];
            String jwtPayloadDecoded = new String(Base64.decodeBase64(jwtPayload));

            JSONObject jwtPayloadJSON = new JSONObject(jwtPayloadDecoded);
            applicationTier = jwtPayloadJSON.getString("http://wso2.org/claims/applicationtier");
        }

        int openOrderCount = 0;

        //get open orders count
        for (Map.Entry<Integer, DetailedOrder> entry : orders.entrySet()) {
            DetailedOrder order = entry.getValue();
            if (order.getCutstomerId().equals(customerId)) {
                openOrderCount++;
            }
        }

        if (openOrderCount == 0) {
            throw new RuntimeException("Customer has no any open order");
        }

        Order[] openOrders;
        // if application tier is "Free-Large" or "Free-Small" return only order id and order header as order details
        // of each order
        if (applicationTier != null) {
            if (applicationTier.equals("Free-Large") || applicationTier.equals("Free-Small")) {
                openOrders = new OrderInBrief[openOrderCount];
                int itr = 0;
                for (Map.Entry<Integer, DetailedOrder> entry : orders.entrySet()) {
                    DetailedOrder order = entry.getValue();
                    if (order.getCutstomerId().equals(customerId)) {
                        OrderInBrief openOrder = new OrderInBrief(order.getOrderId(), order.getOrderHeader());
                        openOrders[itr] = openOrder;
                        itr++;
                    }
                }
                return openOrders;
            } else { //return all the details of each order
                openOrders = new DetailedOrder[openOrderCount];
                int itr = 0;
                for (Map.Entry<Integer, DetailedOrder> entry : orders.entrySet()) {
                    DetailedOrder order = entry.getValue();
                    if (order.getCutstomerId().equals(customerId)) {
                        openOrders[itr] = order;
                        itr++;
                    }
                }
                return openOrders;
            }
        } else { //return all the details of each order
            openOrders = new DetailedOrder[openOrderCount];
            int itr = 0;
            for (Map.Entry<Integer, DetailedOrder> entry : orders.entrySet()) {
                DetailedOrder order = entry.getValue();
                if (order.getCutstomerId().equals(customerId)) {
                    openOrders[itr] = order;
                    itr++;
                }
            }
            return openOrders;
        }

    }

    /**
     * Get the order which has the highest order id, for the given customer
     *
     * @param customerId
     * @return
     */
    public DetailedOrder getMostRecentOrder(String customerId) {
        int mostRecentOrderId = 999;
        if (orders.size() <= 0) {
            throw new RuntimeException("No orders available in the system");
        }
        for (Map.Entry<Integer, DetailedOrder> entry : orders.entrySet()) {
            DetailedOrder order = entry.getValue();
            if (order.getCutstomerId().equals(customerId)) {
                if (order.getOrderId() > mostRecentOrderId) {
                    mostRecentOrderId = order.getOrderId();
                }
            }
        }

        if (mostRecentOrderId == 999) {
            throw new RuntimeException("No orders available for the customer");
        }
        return getOrder(mostRecentOrderId);
    }

    /**
     * Cancel the order (remove the order from the orders map)
     *
     * @param orderId
     * @return orderId the order which was deleted
     */
    public int cancelOrder(int orderId) {

        if (orders.remove(orderId) != null) {
            return orderId;
        } else {
            if (orders.get(orderId) == null) {
                throw new RuntimeException("DetailedOrder with an ID " + orderId + " is not available");
            }
            throw new RuntimeException("DetailedOrder cannot be cancelled");
        }
    }
}