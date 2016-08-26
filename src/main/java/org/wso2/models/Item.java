package org.wso2.models;

/**
Model class realted to predefined item
 */
public class Item {
	private String id;
	private String name;
	private double priceValue;
	private String priceUnit;

	public Item(String id, String name, double priceValue, String priceUnit) {
		this.id = id;
		this.name = name;
		this.priceValue = priceValue;
		this.priceUnit = priceUnit;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(double priceValue) {
		this.priceValue = priceValue;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	
}
