package org.wso2;

public class Order {
	private int orderId;
	private  String cutstomerId;
	private String shippingAddress;
	private String orderHeader;
	private ItemLine[] itemLines; 
	

	public Order(int orderId, String cutstomerId, String shippingAddress,
			String orderHeader, ItemLine[] itemLines) {
		if(isItemIDsValid(itemLines)){
			this.orderId = orderId;
			this.cutstomerId = cutstomerId;
			this.shippingAddress = shippingAddress;
			this.orderHeader = orderHeader;
			this.itemLines = itemLines;
		}else {
			throw new  RuntimeException("Item id invalid");
		}

	}

	private static boolean isItemIDsValid(ItemLine[] itemLines) {
		for(int i = 0; i<itemLines.length; i++){ //for each item line
			String itemId = itemLines[i].getItemId();

			if(!FazioService.items.containsKey(itemId)){
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

	public String getOrderHeader() {
		return orderHeader;
	}

	public void setOrderHeader(String orderHeader) {
		this.orderHeader = orderHeader;
	}
	public ItemLine[] getItemLines() {
		return itemLines;
	}

	public void setItemLines(ItemLine[] itemLines) {
		this.itemLines = itemLines;
	}
	

}
