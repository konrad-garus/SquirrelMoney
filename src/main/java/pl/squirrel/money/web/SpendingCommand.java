package pl.squirrel.money.web;

import java.math.BigDecimal;

public class SpendingCommand {
	private String category;

	private String subcategory;

	private String name;

	private String spendingDate;

	private String totalPrice;

	private Integer quantity;

	private BigDecimal unitPrice;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getSpendingDate() {
		return spendingDate;
	}

	public void setSpendingDate(String spendingDate) {
		this.spendingDate = spendingDate;
	}

}
