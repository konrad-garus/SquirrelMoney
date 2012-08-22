package pl.squirrel.money.web;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import pl.squirrel.money.entity.Spending;

public class SpendingCommand {
	private String category;

	private String subcategory;

	private String name;

	private String spendingDate;

	private LocalDate spendingDateConverted;

	private BigDecimal totalPrice;

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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
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

	public LocalDate getConvertedSpendingDate() {
		return spendingDateConverted;
	}

	public void setSpendingDateConverted(LocalDate spendingDateConverted) {
		this.spendingDateConverted = spendingDateConverted;
	}

	public Spending toSpending() {
		Spending result = new Spending();
		result.setCategory(getCategory());
		result.setSubcategory(getSubcategory());
		result.setName(getName());
		result.setSpendingDate(spendingDateConverted);
		result.setUnitPrice(getUnitPrice());
		if (getUnitPrice() != null
				&& (getQuantity() == null || getQuantity() == 0)) {
			result.setQuantity(1);
		} else {
			result.setQuantity(getQuantity());
		}
		if (getTotalPrice() == null && getUnitPrice() != null) {
			BigDecimal quantity = new BigDecimal(result.getQuantity());
			BigDecimal multiplied = getUnitPrice().multiply(quantity);
			result.setTotalPrice(multiplied);
		} else {
			result.setTotalPrice(getTotalPrice());
		}
		return result;
	}

}
