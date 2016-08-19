package org.wso2;

import java.util.HashMap;
import java.util.Map;

public class FazioService{
	public static Map<String,Item> items = new HashMap<String, Item>();
	private static Map<Integer,Order> orders = new HashMap<Integer,Order>();
	static int orderCount = 1000;
	static{
		items.put("IT001",new Item("IT001","Mouse", 25.0 , "$US") );
		items.put("IT002",new Item("IT002","Kingston Flsh Drive", 25.0 , "$US") );
		items.put("IT003",new Item("IT003","SD card-10gb", 30.0 , "$US") );
		items.put("IT004",new Item("IT004","Cooling Fan", 40.0 , "$US"));
	}

	public Order getOrder(int orderId){
		Order od= orders.get(orderId);
		if(od == null){
			throw new RuntimeException("No order available with the given orderId");
		}
		return od;
	}
	
	public int submitOrder(String customerId, String shippingAddress,String orderHeader, ItemLine[] itemLines) {
		try {
			int orderId = orderCount;
			Order order = new Order(orderId, customerId, shippingAddress, orderHeader, itemLines);
			orders.put(orderId, order);
			orderCount++;
			return orderId;
		}catch (Exception e){
			throw new RuntimeException("Order cannot be submitted");
		}

	}
	
	public OpenOrder[] getOpenOrders(String customerId){
		int openOrderCount = 0;

		//get open orders count
		for (Map.Entry<Integer, Order> entry : orders.entrySet())
		{
			Order order = entry.getValue();
			if(order.getCutstomerId().equals(customerId)){
				openOrderCount++;
			}
		}

		if(openOrderCount == 0){
			throw new RuntimeException("Customer has no any open order");
		}
		OpenOrder[] openOrders = new OpenOrder[openOrderCount];

		int itr=0;
		for (Map.Entry<Integer, Order> entry : orders.entrySet())
		{
			Order order = entry.getValue();
			if(order.getCutstomerId().equals(customerId)){
				OpenOrder openOrder = new OpenOrder(order.getOrderId(),order.getOrderHeader());
				openOrders[itr] = openOrder;
				itr++;
			}
		}
		return openOrders;
	}

	public Order getMostRecentOrder(String customerId){
		int mostRecentOrderId = 999;
		if(orders.size()<=0){
			throw new RuntimeException("No orders available in the system");
		}
		for (Map.Entry<Integer, Order> entry : orders.entrySet())
		{
			Order order = entry.getValue();
			if(order.getCutstomerId().equals(customerId)){
				if(order.getOrderId() > mostRecentOrderId){
					mostRecentOrderId = order.getOrderId();
				}
			}
		}

		if(mostRecentOrderId == 999){
			throw new RuntimeException("No orders available for the customer");
		}
		return getOrder(mostRecentOrderId);
	}

	public int cancelOrder(int orderId){
		if(orders.remove(orderId) != null){
			return orderId;
		}else {
			throw new RuntimeException("Order cannot be cancelled");
		}
	}

}